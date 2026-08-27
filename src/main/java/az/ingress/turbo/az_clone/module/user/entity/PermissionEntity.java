package az.ingress.turbo.az_clone.module.user.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PermissionEntity extends BaseEntity {

    @Column(name = "name", length = 100,
            unique = true, nullable = false)
    String name;

    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    @Builder.Default
    Set<RoleEntity> roles = new LinkedHashSet<>();

}
