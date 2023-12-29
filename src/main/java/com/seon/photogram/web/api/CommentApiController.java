package com.seon.photogram.web.api;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.comment.Comment;
import com.seon.photogram.handler.ex.CustomValidationApiException;
import com.seon.photogram.service.CommentService;
import com.seon.photogram.web.dto.CMRespDto;
import com.seon.photogram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 쓰기
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentWrite(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        }

        Comment comment = commentService.commentWrite(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){
        commentService.commentDelete(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제성공", null), HttpStatus.OK);
    }
}
