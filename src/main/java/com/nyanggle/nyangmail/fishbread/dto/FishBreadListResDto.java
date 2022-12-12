package com.nyanggle.nyangmail.fishbread.dto;

import com.nyanggle.nyangmail.fishbread.persistence.FishBreadStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FishBreadListResDto {
    private Long fishId;
    private String type;
    private String senderNickname;
    private FishBreadStatus status;

    @QueryProjection
    public FishBreadListResDto(Long fishId, String type, String senderNickname, FishBreadStatus status) {
        this.fishId = fishId;
        this.type = type;
        this.senderNickname = senderNickname;
        this.status = status;
    }
}