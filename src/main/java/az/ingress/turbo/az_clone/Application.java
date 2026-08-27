package az.ingress.turbo.az_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
        "az.ingress.turbo.az_clone.module.user.repository.jpa",
        "az.ingress.turbo.az_clone.module.car.repository",
        "az.ingress.turbo.az_clone.module.ai.repository"
})
@EnableRedisRepositories(basePackages =
        "az.ingress.turbo.az_clone.module.user.repository.redis")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}