package az.ingress.turbo.az_clone.module.car.service.impl;

import az.ingress.turbo.az_clone.common.mapper.CarMapper;
import az.ingress.turbo.az_clone.module.car.dto.CarRequestDto;
import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import az.ingress.turbo.az_clone.module.car.entity.BrandEntity;
import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import az.ingress.turbo.az_clone.module.car.entity.ImageEntity;
import az.ingress.turbo.az_clone.module.car.entity.ModelEntity;
import az.ingress.turbo.az_clone.module.car.repository.BrandRepository;
import az.ingress.turbo.az_clone.module.car.repository.CarRepository;
import az.ingress.turbo.az_clone.module.car.repository.ModelRepository;
import az.ingress.turbo.az_clone.module.car.service.CarService;
import az.ingress.turbo.az_clone.module.storage.FileStorageService; // aşağıda yaradırıq
import az.ingress.turbo.az_clone.module.user.entity.UserEntity;
import az.ingress.turbo.az_clone.module.user.repository.jpa.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;
    private final CarMapper carMapper;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public CarResponseDto addCar(CarRequestDto request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("İstifadəçi tapılmadı: " + email));

        BrandEntity brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new EntityNotFoundException("Marka tapılmadı: " + request.brandId()));

        ModelEntity model = modelRepository.findById(request.modelId())
                .orElseThrow(() -> new EntityNotFoundException("Model tapılmadı: " + request.modelId()));

        CarEntity car = carMapper.toEntity(request);
        car.setUser(user);
        car.setBrand(brand);
        car.setModel(model);

        CarEntity saved = carRepository.save(car);
        log.info("Yeni maşın yaradıldı, id={}, user={}", saved.getId(), email);

        return carMapper.toDtoCar(saved);
    }

    @Override
    @Transactional
    public CarResponseDto uploadImages(Long carId, List<MultipartFile> files) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Maşın tapılmadı: " + carId));

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Ən azı 1 şəkil göndərilməlidir");
        }

        Set<ImageEntity> newImages = files.stream()
                .map(file -> {
                    String storedFileName = fileStorageService.store(file);
                    return ImageEntity.builder()
                            .imageUrl("/uploads/cars/" + storedFileName)
                            .isMain(false)
                            .build();
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        car.addAllImages(newImages);

        boolean hasMain = car.getImages().stream().anyMatch(ImageEntity::getIsMain);
        if (!hasMain && !newImages.isEmpty()) {
            newImages.iterator().next().setIsMain(true);
        }

        CarEntity saved = carRepository.save(car);
        log.info("{} ədəd şəkil əlavə olundu, carId={}", files.size(), carId);

        return carMapper.toDtoCar(saved);
    }

    @Override
    @Cacheable(value = "car", key = "#id")
    public CarResponseDto findById(Long id) {
        CarEntity carEntity = carRepository.findById(id)
                .orElseThrow();
        return carMapper.toDtoCar(carEntity);
    }

    @Override
    @Cacheable(value = "user-cars", key = "#email")
    public List<CarResponseDto> findUserCarByEmail(String email) {
        return carRepository.findUserCarByEmail(email)
                .stream()
                .map(carMapper::toDtoCar)
                .collect(Collectors.toList());
    }

}