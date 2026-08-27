package az.ingress.turbo.az_clone.module.auth.controller;

import az.ingress.turbo.az_clone.module.auth.dto.RegisterRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RegisterResponse;
import az.ingress.turbo.az_clone.module.auth.dto.ResendOtpRequest;
import az.ingress.turbo.az_clone.module.auth.dto.VerifyRequest;
import az.ingress.turbo.az_clone.module.auth.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/register")
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/pre")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegisterResponse pre(@RequestBody RegisterRequest request) throws Exception {
        return registerService.preRegisterUser(request);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.CREATED)
    public void verify(@RequestBody VerifyRequest request) {
        registerService.verifyAndRegisterUser(request);
    }

    @PostMapping("/newOtp")
    @ResponseStatus(HttpStatus.CONTINUE)
    public void otp(@RequestBody ResendOtpRequest request) {
        registerService.otpAgain(request);
    }


}
