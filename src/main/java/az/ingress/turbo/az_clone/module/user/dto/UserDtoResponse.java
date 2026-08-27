package az.ingress.turbo.az_clone.module.user.dto;

public record UserDtoResponse(
        Long id,
        String name,
        String surname,
        String phoneNumber,
        String email
) {
}
