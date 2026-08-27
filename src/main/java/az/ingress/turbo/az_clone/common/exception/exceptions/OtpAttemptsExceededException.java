package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class OtpAttemptsExceededException extends BaseException {

    public OtpAttemptsExceededException() {
        super(ErrorCode.OTP_ATTEMPTS_EXCEEDED);
    }
}