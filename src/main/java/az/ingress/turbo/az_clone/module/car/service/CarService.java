package az.ingress.turbo.az_clone.module.car.service;

import az.ingress.turbo.az_clone.module.car.dto.CarRequestDto;
import az.ingress.turbo.az_clone.module.car.dto.CarResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    CarResponseDto addCar(CarRequestDto car);
    CarResponseDto uploadImages(Long carId, List<MultipartFile> files);
    CarResponseDto findById(Long id);
    List<CarResponseDto> findUserCarByEmail(String email);
}
