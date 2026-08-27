package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class InvalidOldPasswordException extends BaseException {

    public InvalidOldPasswordException() {
        super(ErrorCode.INVALID_OLD_PASSWORD);
    }
}
