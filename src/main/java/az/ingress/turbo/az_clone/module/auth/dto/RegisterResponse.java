package az.ingress.turbo.az_clone.module.auth.dto;

public record RegisterResponse(
        String signUpSessionId,
        String otp
) {}
