package az.ingress.turbo.az_clone.module.car.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelEntity extends BaseEntity {

    @Column(name = "name", length = 50, nullable = false)
    String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    Set<CarEntity> cars = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    BrandEntity brand;
}