package com.seon.photogram.service;

import com.seon.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    // 좋아요
    @Transactional
    public void likes(int imageId, int principalId) {
        likesRepository.mLikes(imageId, principalId);
    }

    // 좋아요 취소
    @Transactional
    public void unLikes(int imageId, int principalId) {
        likesRepository.mUnLikes(imageId, principalId);
    }
}
