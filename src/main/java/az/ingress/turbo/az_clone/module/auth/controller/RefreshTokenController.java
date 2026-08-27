package az.ingress.turbo.az_clone.module.auth.controller;

import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenResponse;
import az.ingress.turbo.az_clone.module.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/refresh")
public class RefreshTokenController {
    private final RefreshTokenService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return service.refreshToken(request);
    }
}
