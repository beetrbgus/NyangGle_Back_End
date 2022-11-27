package com.nyanggle.nyangmail.persistence.entity;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity(name = "fishbread")
@Getter
public class FishBread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // F/R 반죽 * 앙금 종류  flour/red bean -> flre
    @Enumerated(EnumType.STRING)
    private FishBreadStatus status;
    private String message;
    private String fishBreadUid;
    private String senderIp;
    private String senderNickname;
    private String receiverUid;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    protected FishBread() {}

    public static FishBread create(FishBreadCreateReqDto reqDto, String fishBreadUid, String uuid) {
        return new FishBread(reqDto.getType(), FishBreadStatus.UNREAD, reqDto.getMessage(),
                fishBreadUid, reqDto.getSenderIp(), reqDto.getSenderNickname(), uuid);
    }

    public FishBread(Long id, String type, FishBreadStatus status, String message, String fishBreadUid, String senderIp, String senderNickname, String receiverUid, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.message = message;
        this.fishBreadUid = fishBreadUid;
        this.senderIp = senderIp;
        this.senderNickname = senderNickname;
        this.receiverUid = receiverUid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public FishBread(String type, FishBreadStatus status, String message, String fishBreadUid, String senderIp, String senderNickname, String receiverUid) {
        this.type = type;
        this.status = status;
        this.message = message;
        this.fishBreadUid = fishBreadUid;
        this.senderIp = senderIp;
        this.senderNickname = senderNickname;
        this.receiverUid = receiverUid;
    }
}