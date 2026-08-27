package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class OtpCooldownActiveException extends BaseException {

    public OtpCooldownActiveException() {
        super(ErrorCode.OTP_COOLDOWN_ACTIVE);
    }
}
