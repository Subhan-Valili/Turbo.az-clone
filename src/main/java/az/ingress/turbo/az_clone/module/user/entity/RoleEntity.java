package az.ingress.turbo.az_clone.module.user.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleEntity extends BaseEntity {

    @Column(name = "name", length = 100,
            unique = true, nullable = false)
    String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    Set<UserEntity> users = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<PermissionEntity> permissions = new LinkedHashSet<>();

}
