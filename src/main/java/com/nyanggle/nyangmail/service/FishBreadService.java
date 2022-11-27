package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;

public interface FishBreadService {
    void create(FishBreadCreateReqDto reqDto, String uUid);
    FishBreadResDto findByFishUid(String uUid, Long fishId);
    void fishBreadstatusChange(Long fishId);
}
