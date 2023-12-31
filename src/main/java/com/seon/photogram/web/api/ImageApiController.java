package com.seon.photogram.web.api;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.image.Image;
import com.seon.photogram.service.ImageService;
import com.seon.photogram.service.LikesService;
import com.seon.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    // 스토리 불러오기 api
    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @PageableDefault(size = 3) Pageable pageable){
        Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }

    // 좋아요 api
    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    // 좋아요 취소 api
    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unLikes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null),HttpStatus.OK);
    }
}
