package az.ingress.turbo.az_clone.module.car.repository;

import az.ingress.turbo.az_clone.module.car.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long> {
}
