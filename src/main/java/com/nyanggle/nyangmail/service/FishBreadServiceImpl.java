package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.persistence.entity.FishBread;
import com.nyanggle.nyangmail.persistence.repository.FishBreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FishBreadServiceImpl implements FishBreadService{

    private final FishBreadRepository fishBreadRepository;
    private final RandomIdUtil randomIdUtil;
    @Override
    public void create(FishBreadCreateReqDto reqDto, String uUid) {
        FishBread fishBread = FishBread.create(reqDto, randomIdUtil.fishBreadId(), uUid);
        fishBreadRepository.save(fishBread);
    }
}
