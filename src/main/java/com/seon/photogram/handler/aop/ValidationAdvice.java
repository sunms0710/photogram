package com.seon.photogram.handler.aop;

import com.seon.photogram.handler.ex.CustomValidationApiException;
import com.seon.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidationAdvice {

    @Around("execution(* com.seon.photogram.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg: args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.seon.photogram.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg: args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
