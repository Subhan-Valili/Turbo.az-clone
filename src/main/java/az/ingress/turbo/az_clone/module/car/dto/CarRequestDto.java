package az.ingress.turbo.az_clone.module.car.dto;

import java.math.BigDecimal;

public record CarRequestDto(
        BigDecimal price,
        Integer year,
        Integer mileage,
        BigDecimal engineVolume,
        Integer hp,
        String description,
        String fuelType,
        String transmission,
        Long brandId,
        Long modelId
) {
}