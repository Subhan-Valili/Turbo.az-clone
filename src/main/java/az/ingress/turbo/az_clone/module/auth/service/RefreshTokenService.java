package az.ingress.turbo.az_clone.module.auth.service;

import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenRequest;
import az.ingress.turbo.az_clone.module.auth.dto.RefreshTokenResponse;

public interface RefreshTokenService {
     RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
