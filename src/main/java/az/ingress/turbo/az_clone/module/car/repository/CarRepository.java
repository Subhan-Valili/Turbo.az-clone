package az.ingress.turbo.az_clone.module.car.repository;

import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> ,
         JpaSpecificationExecutor<CarEntity> {

    @Query(value = " select c from CarEntity c " +
            " left join fetch c.brand " +
            " left join fetch c.model" +
            " left join fetch c.images" +
            " where c.id = :id")
    Optional<CarEntity> findById(@Param(value = "id") Long id);

    @Query(value = "select c from CarEntity c" +
            " left join fetch c.brand" +
            " left join fetch c.model" +
            " left join fetch c.images" +
            " where c.user.email = :email")
    List<CarEntity> findUserCarByEmail(@Param(value = "email") String email);

    @Query(value = "select c from CarEntity c " +
            " left join fetch c.brand" +
            " left join fetch c.model" +
            " where c.indexed = false ")
    List<CarEntity> findByIndexedFalse();
}
