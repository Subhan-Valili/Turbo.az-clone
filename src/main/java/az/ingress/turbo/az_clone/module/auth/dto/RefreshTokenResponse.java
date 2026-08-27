package az.ingress.turbo.az_clone.module.auth.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
