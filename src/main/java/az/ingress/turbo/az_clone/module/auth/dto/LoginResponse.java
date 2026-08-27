package az.ingress.turbo.az_clone.module.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
