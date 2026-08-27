package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class OtpExpiredException extends BaseException {

    public OtpExpiredException() {
        super(ErrorCode.OTP_EXPIRED);
    }
}
