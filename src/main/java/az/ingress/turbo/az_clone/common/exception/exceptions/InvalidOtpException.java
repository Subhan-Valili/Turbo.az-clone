package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class InvalidOtpException extends BaseException {

    public InvalidOtpException(int remainingAttempts) {
        super(ErrorCode.OTP_INVALID, remainingAttempts);
    }
}
