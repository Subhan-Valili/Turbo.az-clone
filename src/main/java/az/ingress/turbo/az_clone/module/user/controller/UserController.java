package az.ingress.turbo.az_clone.module.user.controller;

import az.ingress.turbo.az_clone.module.user.dto.UserDtoRequest;
import az.ingress.turbo.az_clone.module.user.dto.UserDtoResponse;
import az.ingress.turbo.az_clone.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserDtoResponse updateUser(@RequestBody UserDtoRequest request){
        return userService.updateUser(request);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(){
        return userService.deleteUser();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDtoResponse findById(Long id){
        return userService.findById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoResponse> findAll(){
        return userService.findAll();
    }
}
