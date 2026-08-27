package az.ingress.turbo.az_clone.module.user.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import az.ingress.turbo.az_clone.common.enums.Status;
import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "name", length = 100, nullable = false)
    String name;

    @Column(name = "surname", length = 100, nullable = false)
    String surname;

    @Column(name = "phone_number", nullable = false, length = 100)
    String phoneNumber;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status = Status.ACTIVE;

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleEntity> roles = new LinkedHashSet<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<CarEntity> cars = new LinkedHashSet<>();

    public void addCar(CarEntity car) {
        cars.add(car);
        car.setUser(this);
    }

    public void removeCar(CarEntity car) {
        cars.remove(car);
        car.setUser(null);
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getName())),
                        role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getName()))
                ))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}