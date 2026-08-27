package az.ingress.turbo.az_clone.module.auth.service.impl;

import az.ingress.turbo.az_clone.common.exception.exceptions.InvalidRefreshTokenException;
import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenResponse;
import az.ingress.turbo.az_clone.module.auth.security.JwtService;
import az.ingress.turbo.az_clone.module.auth.service.RefreshTokenService;
import az.ingress.turbo.az_clone.module.user.entity.RefreshToken;
import az.ingress.turbo.az_clone.module.user.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository.findById(request.refreshToken())
                .orElseThrow(() -> {
                    log.warn("Refresh token failed. Token not found or expired [ID: {}]", request.refreshToken());
                    return new InvalidRefreshTokenException();
                });

        UserDetails user = userDetailsService.loadUserByUsername(refreshToken.getEmail());

        String accessToken = jwtService.generateToken(user);

        refreshTokenRepository.delete(refreshToken);

        String id = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
                .id(id)
                .email(refreshToken.getEmail())
                .ttl(jwtService.getRefreshExpirationInSeconds())
                .build();

        refreshTokenRepository.save(token);

        log.info("Refresh token rotated successfully for user '{}'. New token ID: {}", refreshToken.getEmail(), id);

        return new RefreshTokenResponse(accessToken, id);
    }
}