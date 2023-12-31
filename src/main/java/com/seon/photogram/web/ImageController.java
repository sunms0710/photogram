package com.seon.photogram.web;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.image.Image;
import com.seon.photogram.handler.ex.CustomValidationException;
import com.seon.photogram.service.ImageService;
import com.seon.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular(Model model){
        List<Image> images = imageService.popularImage();
        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        imageService.imageUpload(imageUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}
