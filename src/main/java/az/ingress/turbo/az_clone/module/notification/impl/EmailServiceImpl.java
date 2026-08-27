package az.ingress.turbo.az_clone.module.notification.impl;

import az.ingress.turbo.az_clone.module.notification.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom secureRandom;


    @Async
    @Override
    public void sendWelcomeEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(text);

            mailSender.send(mail);
            log.info("Email successfully sent to '{}'", to);
        } catch (Exception e) {
            log.error("Email sending failed to '{}': {}", to, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendOtpEmail(String to, String otp) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            String subject = "OTP Code";
            String text = String.format("Bu kodu heç kim ilə paylaşmayın %s", otp);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(text);

            mailSender.send(mail);
            log.info("OTP successfully sent to '{}'", to);
        } catch (Exception e) {
            log.error("OTP sending failed to '{}': {}", to, e.getMessage());
        }

    }

    @Override
    public String generateOtpCode() {
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }
}
