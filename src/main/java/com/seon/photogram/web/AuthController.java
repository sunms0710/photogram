package com.seon.photogram.web;

import com.seon.photogram.domain.user.User;
import com.seon.photogram.service.AuthService;
import com.seon.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
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
    public String siginup(SignupDto signupDto) {
//        log.info(signupDto.toString());
        User user = signupDto.toEntity();
//        log.info(user.toString());
        User userEntity = authService.signup(user);
        log.info(userEntity.toString());
        return "auth/signin";
    }
}
