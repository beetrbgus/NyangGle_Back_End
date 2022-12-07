package com.nyanggle.nyangmail.interfaces.dto.fishbread;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainInfoResDto {
    private Long totalCount;
    private Long unreadCount;
    private String nickname;
}
