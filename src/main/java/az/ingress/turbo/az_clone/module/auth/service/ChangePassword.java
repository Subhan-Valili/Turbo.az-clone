package az.ingress.turbo.az_clone.module.auth.service;


import az.ingress.turbo.az_clone.module.auth.dto.ChangePasswordRequest;

public interface ChangePassword {
    String changePassword(ChangePasswordRequest request);

}
