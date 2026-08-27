package az.ingress.turbo.az_clone.module.car.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import az.ingress.turbo.az_clone.common.enums.Fuel;
import az.ingress.turbo.az_clone.common.enums.Transmission;
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarEntity extends BaseEntity {

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "year", nullable = false)
    Integer year;

    @Column(name = "mileage", nullable = false)
    Integer mileage;

    @Column(name = "engine_volume", nullable = false, precision = 19, scale = 2)
    BigDecimal engineVolume;

    @Column(name = "hp", nullable = false)
    Integer hp;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    Fuel fuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission", nullable = false)
    Transmission transmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ToString.Exclude
    ModelEntity model;

    @ToString.Exclude
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    Set<ImageEntity> images = new LinkedHashSet<>();

    @Builder.Default
    @Column(name = "indexed", nullable = false)
    Boolean indexed = false;


    public void addImage(ImageEntity image) {
        images.add(image);
        image.setCar(this);
    }

    public void addAllImages(Set<ImageEntity> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        images.forEach(this::addImage);
    }
}