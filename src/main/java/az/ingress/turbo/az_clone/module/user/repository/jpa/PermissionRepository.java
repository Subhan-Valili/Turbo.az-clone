package az.ingress.turbo.az_clone.module.user.repository.jpa;

import az.ingress.turbo.az_clone.module.user.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
