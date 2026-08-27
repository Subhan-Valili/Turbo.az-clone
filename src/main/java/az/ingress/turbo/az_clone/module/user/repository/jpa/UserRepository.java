package az.ingress.turbo.az_clone.module.user.repository.jpa;

import az.ingress.turbo.az_clone.common.enums.Status;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoResponse;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u FROM UserEntity u where u.email = :email " +
            "and u.status = az.ingress.turbo.az_clone.common.enums.Status.ACTIVE")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query(value = "SELECT u FROM UserEntity u where u.id = :id " +
            "and u.status = az.ingress.turbo.az_clone.common.enums.Status.ACTIVE")
    Optional<UserEntity> findById(@Param(value = "id") Long id);

    @Query(value = "SELECT u FROM UserEntity u where " +
            "u.status = az.ingress.turbo.az_clone.common.enums.Status.ACTIVE")
    List<UserEntity> findAll();

    @Query(value = "SELECT u FROM UserEntity u where u.email = :email" +
            " and u.status = az.ingress.turbo.az_clone.common.enums.Status.ACTIVE")
    Optional<UserEntity> findByEmailAndStatus(String email);

}
