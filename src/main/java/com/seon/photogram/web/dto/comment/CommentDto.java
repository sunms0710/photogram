package com.seon.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@NotBlank : 빈값, null, " "체크
//@NotEmpty : 빈값, null 체크
//@NotNull : null 체크
@Data
public class CommentDto {

    @NotBlank
    private String content;
    @NotNull
    private Integer imageId;
}
