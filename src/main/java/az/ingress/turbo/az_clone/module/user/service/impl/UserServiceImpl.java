package az.ingress.turbo.az_clone.module.user.service.impl;

import az.ingress.turbo.az_clone.common.enums.Status;
import az.ingress.turbo.az_clone.common.exception.exceptions.UserNotFoundException;
import az.ingress.turbo.az_clone.common.mapper.UserMapper;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoRequest;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoResponse;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import az.ingress.turbo.az_clone.module.user.repository.jpa.UserRepository;
import az.ingress.turbo.az_clone.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    @Cacheable(value = "user-token" , key = "#email")
    public UserDtoResponse findUserBySecurityToken() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        UserEntity entity = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Find user by token failed. User not found for email: {}", email);
                    return new UserNotFoundException();
                });
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#result.id"),
            @CacheEvict(value = "user-token", key = "#result.email")
    })
    public UserDtoResponse updateUser(UserDtoRequest request) {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        UserEntity user = userRepository.findByEmailAndStatus(userEmail)
                .orElseThrow(() -> {
                    log.warn("Update user failed. Active user not found for email: {}", userEmail);
                    return new UserNotFoundException();
                });
        userMapper.updateUser(request, user);
        log.info("User [email: {}] updated successfully", userEmail);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "user", key = "#result.id"),
            @CacheEvict(value = "user-token", key = "#result.email")
    })
    public String deleteUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        UserEntity db = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Delete user failed. User not found for email: {}", email);
                    return new UserNotFoundException();
                });

        db.setStatus(Status.INACTIVE);
        userRepository.save(db);

        log.info("User [ID: {}, email: {}] deleted (deactivated) successfully", db.getId(), email);

        return "İstifadəçi uğurla silindi!";
    }

    @Override
    @Cacheable(value = "user" , key = "#id")
    public UserDtoResponse findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Find user by id failed. User not found [ID: {}]", id);
                    return new UserNotFoundException();
                });
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDtoResponse> findAll() {
        List<UserEntity> users = userRepository.findAll();
        log.info("Fetched {} users", users.size());
        return userMapper.toDto(users);
    }

}