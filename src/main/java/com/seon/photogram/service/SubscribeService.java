package com.seon.photogram.service;

import com.seon.photogram.domain.subscribe.SubScribeRepository;
import com.seon.photogram.handler.ex.CustomApiException;
import com.seon.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubScribeRepository subScribeRepository;
    private final EntityManager em;

    // 구독
    @Transactional
    public void subscribe(int fromUserId, int toUserId){
        try {
            subScribeRepository.mSubscribe(fromUserId, toUserId);
        }catch(Exception e) {
            throw new CustomApiException("이미 구독하였습니다.");
        }

    }
    
    // 구독 취소
    @Transactional
    public void unSubscribe(int fromUserId, int toUserId){
        subScribeRepository.mUnSubscribe(fromUserId, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {
        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, "); // 물음표 principalId
        sb.append("if ((?=u.id), 1, 0) equalUserState "); // 물음표 principalId
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 물음표 pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리 실행 (qlrm 라이브러리 필요 = Dto에 DB결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
        return subscribeDtos;
    }

}
