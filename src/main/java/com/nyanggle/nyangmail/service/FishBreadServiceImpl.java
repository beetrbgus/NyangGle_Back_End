package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import com.nyanggle.nyangmail.exception.fishbread.AlreadyDeletedFishBread;
import com.nyanggle.nyangmail.exception.fishbread.CannotFindFishBread;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;
import com.nyanggle.nyangmail.persistence.entity.FishBread;
import com.nyanggle.nyangmail.persistence.entity.FishBreadStatus;
import com.nyanggle.nyangmail.persistence.repository.CustomFishBreadRepository;
import com.nyanggle.nyangmail.persistence.repository.FishBreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FishBreadServiceImpl implements FishBreadService{

    private final FishBreadRepository fishBreadRepository;
    private final CustomFishBreadRepository customFishBreadRepository;
    private final RandomIdUtil randomIdUtil;

    @Transactional
    @Override
    public void create(FishBreadCreateReqDto reqDto, String uUid, String senderIp) {
        FishBread fishBread = FishBread.create(reqDto, randomIdUtil.fishBreadId(), uUid, senderIp);
        fishBreadRepository.save(fishBread);
    }

    @Transactional(readOnly = true)
    @Override
    public FishBreadResDto findByFishUid(String uUid, Long fishId) {
        FishBread fishBread = fishBreadRepository.findByIdAndReceiverUid(fishId, uUid)
                .orElseThrow(CannotFindFishBread::new);
        if(fishBread.getStatus() == FishBreadStatus.DELETED) {
            throw new AlreadyDeletedFishBread();
        }
        return new FishBreadResDto(fishBread);
    }

    @Transactional
    @Override
    public void fishBreadstatusChange(Long fishId) {
        FishBread fishBread = fishBreadRepository.findById(fishId)
                .orElseThrow(CannotFindFishBread::new);
        if(fishBread.getStatus() == FishBreadStatus.UNREAD) {
            fishBread.read();
        }
    }
    @Transactional(readOnly = true)
    @Override
    public Page<FishBreadListResDto> findBySearchCondition(String uuid, Pageable pageable, SearchCondition searchCondition) {
        return customFishBreadRepository.searchByCondition(uuid, searchCondition, pageable);
    }

    @Override
    public Long findFishBreadCountAll(String cartUUid) {
        return customFishBreadRepository.findFishBreadCountAll(cartUUid);
    }

    @Override
    public Long findFishBreadCountNotRead(String userId) {
        return customFishBreadRepository.findFishBreadCountNotRead(userId);
    }
}
