package az.ingress.turbo.az_clone.module.car.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandEntity extends BaseEntity {

    @Column(name = "name", length = 50, unique = true, nullable = false)
    String name;

    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<CarEntity> cars = new LinkedHashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    Set<ModelEntity> models = new LinkedHashSet<>();
}