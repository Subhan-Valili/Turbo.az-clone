package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class RegistrationSessionExpiredException extends BaseException {

    public RegistrationSessionExpiredException() {
        super(ErrorCode.REGISTRATION_SESSION_EXPIRED);
    }
}
