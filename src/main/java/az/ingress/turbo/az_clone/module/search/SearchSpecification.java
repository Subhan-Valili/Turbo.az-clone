package az.ingress.turbo.az_clone.module.search;

import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import jakarta.persistence.criteria.*;
import org.springframework.lang.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecification implements
        Specification<CarEntity> {

    private final List<SearchCriteria> criteriaList = new ArrayList<>();

    public void search(SearchFilter filter) {

        if (!ObjectUtils.isEmpty(filter.getTransmission())) {
            criteriaList.add(new SearchCriteria("transmission", filter.getTransmission(), SearchOperation.EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getFuelType())) {
            criteriaList.add(new SearchCriteria("fuelType", filter.getFuelType(), SearchOperation.EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getPriceMin())) {
            criteriaList.add(new SearchCriteria("price", filter.getPriceMin(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (!ObjectUtils.isEmpty(filter.getPriceMax())) {
            criteriaList.add(new SearchCriteria("price", filter.getPriceMax(), SearchOperation.LESS_THAN_EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getYearMin())) {
            criteriaList.add(new SearchCriteria("year", filter.getYearMin(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (!ObjectUtils.isEmpty(filter.getYearMax())) {
            criteriaList.add(new SearchCriteria("year", filter.getYearMax(), SearchOperation.LESS_THAN_EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getMileageMin())) {
            criteriaList.add(new SearchCriteria("mileage", filter.getMileageMin(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (!ObjectUtils.isEmpty(filter.getMileageMax())) {
            criteriaList.add(new SearchCriteria("mileage", filter.getMileageMax(), SearchOperation.LESS_THAN_EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getEngineVolumeMin())) {
            criteriaList.add(new SearchCriteria("engineVolume", filter.getEngineVolumeMin(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (!ObjectUtils.isEmpty(filter.getEngineVolumeMax())) {
            criteriaList.add(new SearchCriteria("engineVolume", filter.getEngineVolumeMax(), SearchOperation.LESS_THAN_EQUAL));
        }

        if (!ObjectUtils.isEmpty(filter.getHpMin())) {
            criteriaList.add(new SearchCriteria("hp", filter.getHpMin(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (!ObjectUtils.isEmpty(filter.getHpMax())) {
            criteriaList.add(new SearchCriteria("hp", filter.getHpMax(), SearchOperation.LESS_THAN_EQUAL));
        }
    }


    @Override
    public @Nullable Predicate toPredicate(Root<CarEntity> root,
                                           CriteriaQuery<?> query,
                                           CriteriaBuilder builder) {

        if (!Long.class.equals(query.getResultType()) && !long.class.equals(query.getResultType())) {
            root.fetch("brand", JoinType.LEFT);
            root.fetch("model", JoinType.LEFT);
            root.fetch("images", JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getFirstValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getFirstValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getFirstValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }
}