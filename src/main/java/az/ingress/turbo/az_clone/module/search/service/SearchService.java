package az.ingress.turbo.az_clone.module.search.service;

import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import az.ingress.turbo.az_clone.module.search.SearchFilter;

import java.util.List;

public interface SearchService {

    List<CarResponseDto> search(SearchFilter filter);
}
