package az.ingress.turbo.az_clone.module.car.entity;

import az.ingress.turbo.az_clone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageEntity extends BaseEntity {

    @Column(name = "image_url", nullable = false)
    String imageUrl;

    @Column(name = "is_main", nullable = false)
    @Builder.Default
    Boolean isMain = false;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id",
            nullable = false)
    CarEntity car;
}