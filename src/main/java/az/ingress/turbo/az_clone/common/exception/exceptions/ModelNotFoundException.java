package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class ModelNotFoundException extends BaseException {

    public ModelNotFoundException(Long modelId) {
        super(ErrorCode.MODEL_NOT_FOUND, modelId);
    }
}

