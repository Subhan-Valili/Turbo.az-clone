package az.ingress.turbo.az_clone.module.notification;

public interface EmailService {
    void sendWelcomeEmail(String to, String subject, String text);
    void sendOtpEmail(String to, String otp);
    String generateOtpCode();
}
