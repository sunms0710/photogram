package com.seon.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeDto {
    private int id;
    private String username;
    private String profileImageUrl;
    private boolean subscribeState;
    private boolean equalUserState;
}
