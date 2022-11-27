package com.nyanggle.nyangmail.service;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import com.nyanggle.nyangmail.exception.code.AlreadyDeletedFishBread;
import com.nyanggle.nyangmail.exception.code.CannotFindFishBread;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.persistence.entity.FishBread;
import com.nyanggle.nyangmail.persistence.entity.FishBreadStatus;
import com.nyanggle.nyangmail.persistence.repository.FishBreadRepository;
import com.nyanggle.nyangmail.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FishBreadServiceImpl implements FishBreadService{

    private final UserRepository userRepository;
    private final FishBreadRepository fishBreadRepository;
    private final RandomIdUtil randomIdUtil;

    @Transactional
    @Override
    public void create(FishBreadCreateReqDto reqDto, String uUid) {
        FishBread fishBread = FishBread.create(reqDto, randomIdUtil.fishBreadId(), uUid);
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
}
