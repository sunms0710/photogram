package com.seon.photogram.service;

import com.seon.photogram.domain.subscribe.SubScribeRepository;
import com.seon.photogram.handler.ex.CustomApiException;
import com.seon.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalUserId, int pageUserId) {
        return null;
    }
}
