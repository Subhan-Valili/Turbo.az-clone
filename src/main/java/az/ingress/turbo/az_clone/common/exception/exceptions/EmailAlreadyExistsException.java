package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String email) {
        super(ErrorCode.EMAIL_ALREADY_EXISTS, email);
    }
}
