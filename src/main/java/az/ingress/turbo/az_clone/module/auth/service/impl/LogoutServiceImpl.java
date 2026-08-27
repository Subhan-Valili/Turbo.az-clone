package az.ingress.turbo.az_clone.module.auth.service.impl;

import az.ingress.turbo.az_clone.module.auth.dto.LogoutRequest;
import az.ingress.turbo.az_clone.module.auth.service.LogoutService;
import az.ingress.turbo.az_clone.module.user.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(LogoutRequest request) {
        log.info("Logout process requested for Refresh Token ID: {}", request.token());
        refreshTokenRepository.findById(request.token())
                .ifPresentOrElse(
                        token -> {
                            refreshTokenRepository.delete(token);
                            log.info("User '{}' logged out successfully. Refresh token ID '{}' removed from Redis.",
                                    token.getEmail(), request.token());
                        },
                        () -> log.warn("Logout attempt failed. Refresh token ID '{}' not found or already expired.",
                                request.token())
                );
    }
}