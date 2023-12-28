package com.seon.photogram.service;

import com.seon.photogram.domain.comment.Comment;
import com.seon.photogram.domain.comment.CommentRepository;
import com.seon.photogram.domain.image.Image;
import com.seon.photogram.domain.user.User;
import com.seon.photogram.domain.user.UserRepository;
import com.seon.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 쓰기
    @Transactional
    public Comment commentWrite(String content, int imageId, int userId) {

        // 객체를 만들 때 id 값만 담아서 insert 할 수 있다.
        // 대신 return 시에 image 객체와 user 객체는 id 값만 가지고 있는 빈 객체를 리턴받는다.
        Image image = new Image();
        image.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void commentDelete(int id) {
        commentRepository.deleteById(id);
    }
}
