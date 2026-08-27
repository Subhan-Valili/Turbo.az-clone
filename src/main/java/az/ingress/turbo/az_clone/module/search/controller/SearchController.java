package az.ingress.turbo.az_clone.module.search.controller;

import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.search.SearchFilter;
import az.ingress.turbo.az_clone.module.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CarResponseDto> search(@ModelAttribute SearchFilter filter) {
        return searchService.search(filter);
    }
}
