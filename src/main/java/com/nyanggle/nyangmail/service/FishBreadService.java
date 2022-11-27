package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;

public interface FishBreadService {
    void create(FishBreadCreateReqDto reqDto, String uUid);
}
