package az.ingress.turbo.az_clone.module.auth.service;

import az.ingress.turbo.az_clone.module.auth.dto.LogoutRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface LogoutService {
    void logout(LogoutRequest request);
}
