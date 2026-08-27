package az.ingress.turbo.az_clone.module.auth.controller;

import az.ingress.turbo.az_clone.module.auth.dto.ChangePasswordRequest;
import az.ingress.turbo.az_clone.module.auth.service.ChangePassword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/changePassword")
public class ChangePasswordController {

    private final ChangePassword changePassword;

    @PostMapping
    public String changePassword(@RequestBody ChangePasswordRequest request){
        return changePassword.changePassword(request);
    }
}
