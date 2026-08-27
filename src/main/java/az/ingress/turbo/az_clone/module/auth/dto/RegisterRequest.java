package az.ingress.turbo.az_clone.module.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;

public record RegisterRequest(
        String name,
        String surname,
        String phoneNumber,
        String email,
        String password,
        String rePassword
) {
    @JsonIgnore
    @AssertTrue(message = "Parollar bir-biri ilə üst-üstə düşmür")
    public boolean isPasswordMatching() {
        if (password == null || rePassword == null) {
            return true;
        }
        return password.equals(rePassword);
    }
}
