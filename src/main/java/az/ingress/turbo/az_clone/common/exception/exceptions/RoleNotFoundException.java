package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException(String roleName) {
        super(ErrorCode.ROLE_NOT_FOUND, roleName);
    }
}
