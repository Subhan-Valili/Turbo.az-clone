package az.ingress.turbo.az_clone.module.user.service;

import az.ingress.turbo.az_clone.module.user.dto.UserDtoRequest;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoResponse;

import java.util.List;

public interface UserService {
    UserDtoResponse findUserBySecurityToken();
    UserDtoResponse updateUser(UserDtoRequest request);
    String deleteUser();
    UserDtoResponse findById(Long id);
    List<UserDtoResponse> findAll();
}
