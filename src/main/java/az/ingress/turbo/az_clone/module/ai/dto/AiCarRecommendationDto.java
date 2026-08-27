package az.ingress.turbo.az_clone.module.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiCarRecommendationDto {
    private String summaryMessage; // AI-ın ümumi cavabı/tövsiyəsi
    private List<RecommendedCar> recommendedCars; // Tapılan uyğun maşınların siyahısı

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedCar {
        private Long carId;
        private String brand;
        private String model;
        private Double price;
        private Integer year;
        private String reason; // Niyə bu maşını məsləhət görür
    }
}
