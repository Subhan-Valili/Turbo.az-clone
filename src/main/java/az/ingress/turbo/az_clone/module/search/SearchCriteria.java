package az.ingress.turbo.az_clone.module.search;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCriteria<E1, E2> {

    String key;
    E1 firstValue;
    E2 secondValue;
    SearchOperation operation;

    public SearchCriteria(String key, E1 firstValue, SearchOperation operation) {
        this.key = key;
        this.firstValue = firstValue;
        this.operation = operation;
    }

//    public SearchCriteria(String key, E1 firstValue, E2 secondValue, SearchOperation operation) {
//        this.key = key;
//        this.firstValue = firstValue;
//        this.secondValue = secondValue;
//        this.operation = operation;
//    }
}