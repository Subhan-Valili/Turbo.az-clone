package az.ingress.turbo.az_clone.module.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
