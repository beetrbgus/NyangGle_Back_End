package com.nyanggle.nyangmail.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity(name = "user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String displayName;
    private String email;
    private String nameAttributeKey;
    @Column()
    private String userUid;
    private String domesticId; // provider 내 에서 유니크한 아이디
    private String providerType;
    @Enumerated(EnumType.STRING)
    private Role role;
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

    public User(String nickname, String displayName, String email, String nameAttributeKey, String userUid, String domesticId, String providerType, Role role) {
        this.nickname = nickname;
        this.displayName = displayName;
        this.email = email;
        this.nameAttributeKey = nameAttributeKey;
        this.userUid = userUid;
        this.domesticId = domesticId;
        this.providerType = providerType;
        this.role = role;
    }
}

