package az.ingress.turbo.az_clone.module.car.controller;

import az.ingress.turbo.az_clone.module.car.dto.CarRequestDto;
import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponseDto> addCar(@Valid @RequestBody CarRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.addCar(request));
    }

    @PostMapping(value = "/{carId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarResponseDto> uploadImages(
            @PathVariable Long carId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        return ResponseEntity.ok(carService.uploadImages(carId, files));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @GetMapping("/user-ads")
    public ResponseEntity<List<CarResponseDto>> findAllUserAds() {
        String email = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return ResponseEntity.ok(carService.findUserCarByEmail(email));
    }
}