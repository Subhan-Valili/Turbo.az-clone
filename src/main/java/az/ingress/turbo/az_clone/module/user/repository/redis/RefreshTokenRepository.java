package az.ingress.turbo.az_clone.module.user.repository.redis;

import az.ingress.turbo.az_clone.module.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Consumer;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByEmail(String email);

}
