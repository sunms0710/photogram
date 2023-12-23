package com.seon.photogram.service;

import com.seon.photogram.domain.comment.Comment;
import com.seon.photogram.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 쓰기
    @Transactional
    public Comment commentWrite() {
        return  null;
    }

    // 댓글 삭제
    @Transactional
    public void commentDelete() {

    }
}
