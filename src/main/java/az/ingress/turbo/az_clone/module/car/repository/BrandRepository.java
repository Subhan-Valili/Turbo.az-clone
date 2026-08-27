package az.ingress.turbo.az_clone.module.car.repository;

import az.ingress.turbo.az_clone.module.car.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    @Query(value = "select b from BrandEntity b where b.id = :id")
    Optional<BrandEntity> findById(@Param(value = "id") Long id);
}
