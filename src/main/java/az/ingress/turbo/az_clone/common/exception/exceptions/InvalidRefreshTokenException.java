package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class InvalidRefreshTokenException extends BaseException {

    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
