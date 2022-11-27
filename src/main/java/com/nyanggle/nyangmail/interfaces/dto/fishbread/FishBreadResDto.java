package com.nyanggle.nyangmail.interfaces.dto.fishbread;

import com.nyanggle.nyangmail.persistence.entity.FishBread;
import com.nyanggle.nyangmail.persistence.entity.FishBreadStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class FishBreadResDto {
    private Long id;
    private String fishBreadUid;
    private String type;
    private String message;
    private String senderNickname;
    private FishBreadStatus status;
    private LocalDateTime createdAt;

    public FishBreadResDto(FishBread fishBread) {
        this.id = fishBread.getId();
        this.type = fishBread.getType();
        this.fishBreadUid = fishBread.getFishBreadUid();
        this.message = fishBread.getMessage();
        this.senderNickname = fishBread.getSenderNickname();
        this.status = fishBread.getStatus();
        this.createdAt = fishBread.getCreatedAt();
    }
}
