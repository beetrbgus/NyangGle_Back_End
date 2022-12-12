package com.nyanggle.nyangmail.fishbread.service;

import com.nyanggle.nyangmail.fishbread.dto.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.fishbread.dto.FishBreadListResDto;
import com.nyanggle.nyangmail.fishbread.dto.FishBreadResDto;
import com.nyanggle.nyangmail.fishbread.dto.MainInfoResDto;
import com.nyanggle.nyangmail.fishbread.dto.SearchCondition;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FishBreadService {
    void create(FishBreadCreateReqDto reqDto, String uuid);
    FishBreadResDto findByFishUid(String uUid, Long fishId);
    void fishBreadstatusChange(Long fishId);
    Page<FishBreadListResDto> findBySearchCondition(String uuid, Pageable pageable, SearchCondition searchCondition);
    void deleteFishBread(Long fishId, String userId);
    MainInfoResDto getMainInfo(String cartUUid, UserToken userToken);
}
