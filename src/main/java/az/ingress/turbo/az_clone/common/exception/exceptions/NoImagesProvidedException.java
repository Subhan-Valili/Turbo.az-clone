package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class NoImagesProvidedException extends BaseException {

    public NoImagesProvidedException() {
        super(ErrorCode.NO_IMAGES_PROVIDED);
    }
}