package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FishBreadService {
    void create(FishBreadCreateReqDto reqDto, String uuid);
    FishBreadResDto findByFishUid(String uUid, Long fishId);
    void fishBreadstatusChange(Long fishId);
    Page<FishBreadListResDto> findBySearchCondition(String uuid, Pageable pageable, SearchCondition searchCondition);
    Long findFishBreadCountAll(String cartUUid);
    Long findFishBreadCountNotRead(String userId);
    void deleteFishBread(Long fishId, String userId);
}
