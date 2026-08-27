package az.ingress.turbo.az_clone.module.search.service.impl;

import az.ingress.turbo.az_clone.common.mapper.CarMapper;
import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import az.ingress.turbo.az_clone.module.car.repository.CarRepository;
import az.ingress.turbo.az_clone.module.search.SearchFilter;
import az.ingress.turbo.az_clone.module.search.SearchSpecification;
import az.ingress.turbo.az_clone.module.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final CarRepository carRepository;
    private final CarMapper mapper;


    @Override
    public List<CarResponseDto> search(SearchFilter filter) {
        SearchSpecification searchSpecification = new SearchSpecification();
        searchSpecification.search(filter);
        List<CarEntity> carEntities = carRepository.findAll(searchSpecification);

        return mapper.toCarResponseDtoList(carEntities);
    }
}
