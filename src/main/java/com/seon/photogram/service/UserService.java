package com.seon.photogram.service;

import com.seon.photogram.domain.subscribe.SubScribeRepository;
import com.seon.photogram.domain.user.User;
import com.seon.photogram.domain.user.UserRepository;
import com.seon.photogram.handler.ex.CustomException;
import com.seon.photogram.handler.ex.CustomValidationApiException;
import com.seon.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubScribeRepository subScribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        return userProfileDto;
    }

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
}
