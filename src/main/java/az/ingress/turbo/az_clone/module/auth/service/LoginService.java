package az.ingress.turbo.az_clone.module.auth.service;

import az.ingress.turbo.az_clone.module.auth.dto.LoginRequest;
import az.ingress.turbo.az_clone.module.auth.dto.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request);
}
