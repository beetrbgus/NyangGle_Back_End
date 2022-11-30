package com.nyanggle.nyangmail.interfaces.dto.fishbread;

import com.nyanggle.nyangmail.persistence.entity.FishBreadStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {
    private Long fishId; // 붕어빵 아이디
    private FishBreadStatus status; // 읽음, 안 읽음
    private String callType; //이전 , 다음
}
