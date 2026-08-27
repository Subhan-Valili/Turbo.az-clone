package az.ingress.turbo.az_clone.module.auth.service.impl;

import az.ingress.turbo.az_clone.module.auth.dto.LoginRequest;
import az.ingress.turbo.az_clone.module.auth.dto.LoginResponse;
import az.ingress.turbo.az_clone.module.auth.security.JwtService;
import az.ingress.turbo.az_clone.module.auth.service.LoginService;
import az.ingress.turbo.az_clone.module.user.entity.RefreshToken;
import az.ingress.turbo.az_clone.module.user.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.email());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (request.email(), request.password())
            );
        } catch (BadCredentialsException ex) {
            log.warn("Login failed. Invalid credentials for email: {}", request.email());
            throw ex;
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.email());


        String accessToken = jwtService.generateToken(userDetails);

        refreshTokenRepository.findByEmail(userDetails.getUsername())
                .ifPresent(existingToken -> {
                    log.info("Old refresh token found for user '{}'. Deleting from Redis...", request.email());
                    refreshTokenRepository.delete(existingToken);
                });

        String id = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .id(id)
                .email(request.email())
                .ttl(jwtService.getRefreshExpirationInSeconds())
                .build();

        refreshTokenRepository.save(refreshToken);

        log.info("User '{}' logged in successfully. Refresh token ID: {}", request.email(), id);

        return new LoginResponse(accessToken, id);
    }
}