package az.ingress.turbo.az_clone.common.exception.exceptions;

import az.ingress.turbo.az_clone.common.exception.BaseException;
import az.ingress.turbo.az_clone.common.exception.ErrorCode;

public class CarNotFoundException extends BaseException {

    public CarNotFoundException(Long carId) {
        super(ErrorCode.CAR_NOT_FOUND, carId);
    }
}