package com.seon.photogram.web.api;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.user.User;
import com.seon.photogram.handler.ex.CustomValidationApiException;
import com.seon.photogram.service.SubscribeService;
import com.seon.photogram.service.UserService;
import com.seon.photogram.web.dto.CMRespDto;
import com.seon.photogram.web.dto.subscribe.SubscribeDto;
import com.seon.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    // 유저 프로필 페이지 사진 변경 api
    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.profileImageUpdate(principalId, profileImageFile);
        principalDetails.setUser(userEntity); // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필 사진 변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        } else {
            User userEntity = userService.userEdit(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity);
            return new CMRespDto<>(1, "회원수정완료", userEntity);
        }
    }
}
