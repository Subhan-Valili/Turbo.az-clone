package az.ingress.turbo.az_clone.module.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword,
        String newPasswordAgain
) {
    @JsonIgnore
    @AssertTrue(message = "Parollar bir-biri ilə üst-üstə düşmür")
    public boolean isPasswordMatching() {
        if (newPassword == null || newPasswordAgain == null) {
            return true;
        }
        return newPassword.equals(newPasswordAgain);
    }
}
