package az.ingress.turbo.az_clone.common.mapper;

import az.ingress.turbo.az_clone.module.user.dto.UserDtoRequest;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoResponse;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserDtoResponse toDto(UserEntity user);
    UserEntity toEntity(UserDtoRequest request);
    void updateUser(UserDtoRequest request, @MappingTarget UserEntity user);
    List<UserDtoResponse> toDto(List<UserEntity> users);
}
