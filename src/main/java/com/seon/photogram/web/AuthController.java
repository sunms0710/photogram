package com.seon.photogram.web;

import com.seon.photogram.domain.user.User;
import com.seon.photogram.service.AuthService;
import com.seon.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String sigininForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String siginupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String siginup(@Valid SignupDto signupDto, BindingResult bindingResult) {
            User user = signupDto.toEntity();
            authService.signup(user);
            return "auth/signin";
    }
}
