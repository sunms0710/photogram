package com.seon.photogram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubScribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying // INSERT, DELETE, UPDATE를 nativeQuery로 작성하려면 필요
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);
}
