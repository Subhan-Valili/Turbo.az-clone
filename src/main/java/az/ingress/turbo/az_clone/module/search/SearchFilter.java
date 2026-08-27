package az.ingress.turbo.az_clone.module.search;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchFilter {

    // Qiymət aralığı filteri
    BigDecimal priceMin;
    BigDecimal priceMax;

    // Buraxılış ili aralığı filteri
    Integer yearMin;
    Integer yearMax;

    // Yürüş (KM) aralığı filteri
    Integer mileageMin;
    Integer mileageMax;

    // Mühərrik həcmi aralığı filteri (Məs: 2.0 - 3.5 arası)
    Double engineVolumeMin;
    Double engineVolumeMax;

    // At gücü aralığı filteri
    Integer hpMin;
    Integer hpMax;

    // Yanacaq növü və Sürətlər qutusu (Tam bərabərlik üçün)
    String fuelType;       // Benzin, Dizel, Hibrid və s.
    String transmission;   // Avtomat, Mexanika
}
