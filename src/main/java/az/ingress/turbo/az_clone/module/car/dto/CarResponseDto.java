package az.ingress.turbo.az_clone.module.car.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarResponseDto(
        Long id,
        BigDecimal price,
        Integer year,
        Integer mileage,
        BigDecimal engineVolume,
        Integer hp,
        String description,
        String fuelType,
        String transmission,
        BrandResponseDto brand,
        ModelResponseDto model,
        List<ImageResponseDto> images
) {
    public record BrandResponseDto(
            Long id,
            String name
    ) {
    }

    public record ModelResponseDto(
            Long id,
            String name
    ) {
    }

    public record ImageResponseDto(
            Long id,
            String imageUrl,
            Boolean isMain
    ) {
    }
}