package com.seon.photogram.service;

import com.seon.photogram.domain.subscribe.SubScribeRepository;
import com.seon.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubScribeRepository subScribeRepository;

    @Transactional
    public void subscribe(int fromUserId, int toUserId){
        try {
            subScribeRepository.mSubscribe(fromUserId, toUserId);
        }catch(Exception e) {
            throw new CustomApiException("이미 구독하였습니다.");
        }

    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId){
        subScribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
