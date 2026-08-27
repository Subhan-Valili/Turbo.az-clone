package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
