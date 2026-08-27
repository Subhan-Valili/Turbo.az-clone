package az.ingress.turbo.az_clone.module.auth.service.impl;

import az.ingress.turbo.az_clone.common.exception.exceptions.InvalidOldPasswordException;
import az.ingress.turbo.az_clone.common.exception.exceptions.SamePasswordException;
import az.ingress.turbo.az_clone.common.exception.exceptions.UserNotFoundException;
import az.ingress.turbo.az_clone.module.auth.dto.ChangePasswordRequest;
import az.ingress.turbo.az_clone.module.auth.service.ChangePassword;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import az.ingress.turbo.az_clone.module.user.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordImpl implements ChangePassword {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public String changePassword(ChangePasswordRequest request) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.warn("Change password failed. User not found for email: {}", currentEmail);
                    return new UserNotFoundException();
                });

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            log.warn("Change password failed. Old password mismatch for user [ID: {}]", user.getId());
            throw new InvalidOldPasswordException();
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            log.warn("Change password failed. New password is the same as old for user [ID: {}]", user.getId());
            throw new SamePasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        log.info("İstifadəçi [ID: {}] parolunu uğurla dəyişdi", user.getId());
        return "ugurla deyisildi!";
    }
}