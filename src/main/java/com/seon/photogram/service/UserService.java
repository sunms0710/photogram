package com.seon.photogram.service;

import com.seon.photogram.domain.subscribe.SubScribeRepository;
import com.seon.photogram.domain.user.User;
import com.seon.photogram.domain.user.UserRepository;
import com.seon.photogram.handler.ex.CustomException;
import com.seon.photogram.handler.ex.CustomValidationApiException;
import com.seon.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubScribeRepository subScribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 프로필
    @Transactional(readOnly = true)
    public UserProfileDto userProfile(int pageUserId, int principalId) {
        UserProfileDto userProfileDto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
           throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId);
        userProfileDto.setImageCount(userEntity.getImages().size());

        int subscribeState = subScribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subScribeRepository.mSubscribeCount(pageUserId);

        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);

        //좋아요 카운트 추가
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });
        return userProfileDto;
    }

    // 회원 정보 수정
    @Transactional
    public User userEdit(int id, User user){
        User userEntity = userRepository.findById(id).orElseThrow(() ->
            { return new CustomValidationApiException("찾을 수 없는 id입니다.");});

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

    // 회원 프로필 사진 변경
    @Value("${file.path}")
    private String filePath;

    @Transactional
    public User profileImageUpdate(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(filePath + imageFileName);

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
           throw new CustomValidationApiException("유저를 찾을 수 없습니다.");
        });

        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }
}
