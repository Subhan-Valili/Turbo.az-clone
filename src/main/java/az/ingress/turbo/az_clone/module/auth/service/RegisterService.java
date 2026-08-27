package az.ingress.turbo.az_clone.module.auth.service;

import az.ingress.turbo.az_clone.module.auth.dto.RegisterRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RegisterResponse;
import az.ingress.turbo.az_clone.module.auth.dto.ResendOtpRequest;
import az.ingress.turbo.az_clone.module.auth.dto.VerifyRequest;

public interface RegisterService {
    RegisterResponse preRegisterUser(RegisterRequest request) throws Exception;
    void verifyAndRegisterUser(VerifyRequest request);
    void otpAgain(ResendOtpRequest request);
}
