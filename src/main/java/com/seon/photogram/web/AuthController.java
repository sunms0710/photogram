package com.seon.photogram.web;

import com.seon.photogram.domain.user.User;
import com.seon.photogram.handler.ex.CustomValidationException;
import com.seon.photogram.service.AuthService;
import com.seon.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public String siginup(@Valid SignupDto signupDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        } else {
            User user = signupDto.toEntity();
            authService.signup(user);
            return "auth/signin";
        }

    }
}
