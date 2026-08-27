package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class BrandNotFoundException extends BaseException {

    public BrandNotFoundException(Long brandId) {
        super(ErrorCode.BRAND_NOT_FOUND, brandId);
    }
}
