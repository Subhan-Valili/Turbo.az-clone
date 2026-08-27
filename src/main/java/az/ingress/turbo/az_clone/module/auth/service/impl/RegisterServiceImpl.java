package az.ingress.turbo.az_clone.module.auth.service.impl;

import az.ingress.turbo.az_clone.common.exception.exceptions.*;
import az.ingress.turbo.az_clone.module.auth.dto.RegisterRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RegisterResponse;
import az.ingress.turbo.az_clone.module.auth.dto.ResendOtpRequest;
import az.ingress.turbo.az_clone.module.auth.dto.VerifyRequest;
import az.ingress.turbo.az_clone.module.auth.service.RegisterService;
import az.ingress.turbo.az_clone.module.notification.EmailService;
import az.ingress.turbo.az_clone.module.user.entity.RoleEntity;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import az.ingress.turbo.az_clone.module.user.repository.jpa.RoleRepository;
import az.ingress.turbo.az_clone.module.user.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse preRegisterUser(RegisterRequest request) {
        String email = request.email();
        log.info("Registration attempt initiated for email: {}", email);

        if (userRepository.findByEmail(email).isPresent()) {
            log.warn("Registration failed. Email already in use: {}", email);
            throw new EmailAlreadyExistsException(email);
        }

        String signUpSessionId = UUID.randomUUID().toString();

        String regDataKey = "reg:session:" + signUpSessionId;
        String otpKey = "reg:otp:" + signUpSessionId;
        String cooldownKey = "reg:cooldown:" + signUpSessionId;

        String otp = emailService.generateOtpCode();

        emailService.sendOtpEmail(email, otp);

        redisTemplate.opsForValue().set(regDataKey, request, 15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(otpKey, otp, 3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(cooldownKey, "blocked", 1, TimeUnit.MINUTES);

        log.info("Registration session created with ID: {}", signUpSessionId);

        return new RegisterResponse(signUpSessionId, "OTP kodu e-poçt ünvanınıza göndərildi!");
    }

    @Override
    @Transactional
    public void verifyAndRegisterUser(VerifyRequest request) {
        String sessionId = request.signUpSessionId();

        String regDataKey = "reg:session:" + sessionId;
        String otpKey = "reg:otp:" + sessionId;
        String attemptKey = "reg:attempts:" + sessionId;
        String cooldownKey = "reg:cooldown:" + sessionId;

        RegisterRequest registerRequest = (RegisterRequest) redisTemplate.opsForValue().get(regDataKey);
        if (registerRequest == null) {
            log.warn("Verification failed. Registration session expired or not found [Session ID: {}]", sessionId);
            throw new RegistrationSessionExpiredException();
        }

        String cachedOtp = (String) redisTemplate.opsForValue().get(otpKey);
        if (cachedOtp == null) {
            log.warn("Verification failed. OTP expired [Session ID: {}]", sessionId);
            throw new OtpExpiredException();
        }

        if (!cachedOtp.equals(request.otp())) {
            Long currentAttempts = redisTemplate.opsForValue().increment(attemptKey, 1);

            if (currentAttempts != null && currentAttempts == 1) {
                redisTemplate.expire(attemptKey, 3, TimeUnit.MINUTES);
            }

            long attemptsCount = currentAttempts != null ? currentAttempts : 1;
            int remaining = 3 - (int) attemptsCount;

            if (remaining <= 0) {
                redisTemplate.delete(otpKey);
                redisTemplate.delete(attemptKey);
                log.warn("Verification failed. Max OTP attempts exceeded [Session ID: {}]", sessionId);
                throw new OtpAttemptsExceededException();
            }

            log.warn("Verification failed. Invalid OTP provided [Session ID: {}]. Remaining attempts: {}",
                    sessionId, remaining);
            throw new InvalidOtpException(remaining);
        }

        RoleEntity role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> {
                    log.error("ROLE_USER verilənlər bazasında tapılmadı!");
                    return new RoleNotFoundException("ROLE_USER");
                });

        UserEntity user = UserEntity.builder()
                .name(registerRequest.name())
                .surname(registerRequest.surname())
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        user.getRoles().add(role);

        userRepository.save(user);

        String subject = "Welcome to Authentication System";
        String text = String.format("%s %s Xoş Gelmisiniz!", registerRequest.name(), registerRequest.surname());
        emailService.sendWelcomeEmail(registerRequest.email(), subject, text);

        redisTemplate.delete(otpKey);
        redisTemplate.delete(attemptKey);
        redisTemplate.delete(regDataKey);
        redisTemplate.delete(cooldownKey);

        log.info("User registered successfully. Email: {}", registerRequest.email());
    }

    @Override
    public void otpAgain(ResendOtpRequest request) {
        String sessionId = request.signUpSessionId();

        String regDataKey = "reg:session:" + sessionId;
        String cooldownKey = "reg:cooldown:" + sessionId;
        String otpKey = "reg:otp:" + sessionId;
        String attemptKey = "reg:attempts:" + sessionId;

        RegisterRequest registerRequest = (RegisterRequest) redisTemplate.opsForValue().get(regDataKey);
        if (registerRequest == null) {
            log.warn("Resend OTP failed. Registration session expired [Session ID: {}]", sessionId);
            throw new RegistrationSessionExpiredException();
        }

        Boolean isCooldownActive = redisTemplate.hasKey(cooldownKey);
        if (Boolean.TRUE.equals(isCooldownActive)) {
            log.warn("Resend OTP failed. Cooldown still active [Session ID: {}]", sessionId);
            throw new OtpCooldownActiveException();
        }

        redisTemplate.delete(otpKey);
        redisTemplate.delete(attemptKey);

        String otp = emailService.generateOtpCode();

        emailService.sendOtpEmail(registerRequest.email(), otp);

        redisTemplate.opsForValue().set(otpKey, otp, 3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(cooldownKey, "blocked", 1, TimeUnit.MINUTES);

        log.info("New OTP sent for Session ID: {}", sessionId);
    }
}