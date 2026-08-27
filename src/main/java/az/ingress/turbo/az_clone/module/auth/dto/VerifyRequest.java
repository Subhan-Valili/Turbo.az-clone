package az.ingress.turbo.az_clone.module.auth.dto;

public record VerifyRequest(
        String signUpSessionId,
        String otp
) {
}
