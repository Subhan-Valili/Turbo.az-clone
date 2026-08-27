package az.ingress.turbo.az_clone.module.auth.controller;

import az.ingress.turbo.az_clone.module.auth.dto.LogoutRequest;
import az.ingress.turbo.az_clone.module.auth.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/logout")
public class LogoutController {
    private final LogoutService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(LogoutRequest request) {
        service.logout(request);

    }
}
