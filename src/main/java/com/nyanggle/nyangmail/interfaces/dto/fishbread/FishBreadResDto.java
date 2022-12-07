package com.nyanggle.nyangmail.interfaces.dto.fishbread;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
