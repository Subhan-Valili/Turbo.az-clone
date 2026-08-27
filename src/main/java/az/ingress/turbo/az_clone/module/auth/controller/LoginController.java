package az.ingress.turbo.az_clone.module.auth.controller;

import az.ingress.turbo.az_clone.module.auth.dto.LoginRequest;
import az.ingress.turbo.az_clone.module.auth.dto.LoginResponse;
import az.ingress.turbo.az_clone.module.auth.service.LoginService;
import az.ingress.turbo.az_clone.module.user.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
