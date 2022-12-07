package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFishBreadRepository {
    Page<FishBreadListResDto> searchByCondition(String uUid, SearchCondition searchCondition, Pageable pageable);
    Long findFishBreadCountAll(String cartUUid);
    Long findFishBreadCountUnRead(String uUid, int maxCount);
}
