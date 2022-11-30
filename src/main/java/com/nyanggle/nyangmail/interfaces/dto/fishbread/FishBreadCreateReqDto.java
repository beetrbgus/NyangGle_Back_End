package com.nyanggle.nyangmail.interfaces.dto.fishbread;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FishBreadCreateReqDto {
    @NotBlank
    private String type;
    @NotBlank
    private String message;
    @NotBlank
    private String senderIp;
    @NotBlank
    private String senderNickname;
    private String receiverUid;
}
