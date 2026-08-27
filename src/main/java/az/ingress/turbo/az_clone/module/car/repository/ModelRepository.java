package az.ingress.turbo.az_clone.module.car.repository;

import az.ingress.turbo.az_clone.module.car.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity,Long> {

    @Query(value = "select m from ModelEntity m where m.id = :id")
    Optional<ModelEntity> findById(@Param("id") Long id);
}
