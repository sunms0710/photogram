package com.seon.photogram.web;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.service.UserService;
import com.seon.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserProfileDto userProfileDto = userService.userProfile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("userProfileDto", userProfileDto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(){
        return "user/update";
    }

}
