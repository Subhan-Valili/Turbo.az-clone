package az.ingress.turbo.az_clone.common.mapper;

import az.ingress.turbo.az_clone.module.car.dto.CarRequestDto;
import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import az.ingress.turbo.az_clone.module.car.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CarMapper {

    List<CarResponseDto> toCarResponseDtoList(List<CarEntity> cars);

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "images", source = "images")
    CarResponseDto toDtoCar(CarEntity car);

    CarResponseDto.BrandResponseDto toDtoBrand(az.ingress.turbo.az_clone.module.car.entity.BrandEntity brand);

    CarResponseDto.ModelResponseDto toDtoModel(az.ingress.turbo.az_clone.module.car.entity.ModelEntity model);

    CarResponseDto.ImageResponseDto toDtoImage(ImageEntity image);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "user", ignore = true)
    CarEntity toEntity(CarRequestDto request);
}