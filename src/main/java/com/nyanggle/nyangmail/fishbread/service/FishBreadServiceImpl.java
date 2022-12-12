package com.nyanggle.nyangmail.fishbread.service;

import com.nyanggle.nyangmail.common.RandomIdUtil;
import com.nyanggle.nyangmail.exception.fishbread.AlreadyDeletedFishBread;
import com.nyanggle.nyangmail.exception.fishbread.CannotFindFishBread;
import com.nyanggle.nyangmail.exception.user.CannotFindUser;
import com.nyanggle.nyangmail.fishbread.dto.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.fishbread.dto.FishBreadListResDto;
import com.nyanggle.nyangmail.fishbread.dto.FishBreadResDto;
import com.nyanggle.nyangmail.fishbread.dto.MainInfoResDto;
import com.nyanggle.nyangmail.fishbread.dto.SearchCondition;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import com.nyanggle.nyangmail.fishbread.persistence.FishBread;
import com.nyanggle.nyangmail.fishbread.persistence.FishBreadStatus;
import com.nyanggle.nyangmail.user.persistence.User;
import com.nyanggle.nyangmail.fishbread.repository.CustomFishBreadRepository;
import com.nyanggle.nyangmail.fishbread.repository.FishBreadRepository;
import com.nyanggle.nyangmail.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FishBreadServiceImpl implements FishBreadService{

    private final UserRepository userRepository;
    private final FishBreadRepository fishBreadRepository;
    private final CustomFishBreadRepository customFishBreadRepository;
    private final RandomIdUtil randomIdUtil;

    @Override
    @Transactional
    public void create(FishBreadCreateReqDto reqDto, String uuid) {
        FishBread fishBread = FishBread.create(reqDto, randomIdUtil.fishBreadId(), uuid);
        fishBreadRepository.save(fishBread);
    }

    @Override
    @Transactional(readOnly = true)
    public FishBreadResDto findByFishUid(String uUid, Long fishId) {
        FishBread fishBread = fishBreadRepository.findByIdAndReceiverUid(fishId, uUid)
                .orElseThrow(CannotFindFishBread::new);
        if(fishBread.getStatus() == FishBreadStatus.DELETED) {
            throw new AlreadyDeletedFishBread();
        }
        return new FishBreadResDto(fishBread);
    }

    @Override
    @Transactional
    public void fishBreadstatusChange(Long fishId) {
        FishBread fishBread = fishBreadRepository.findById(fishId)
                .orElseThrow(CannotFindFishBread::new);
        if(fishBread.getStatus() == FishBreadStatus.UNREAD) {
            fishBread.read();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FishBreadListResDto> findBySearchCondition(String uuid, Pageable pageable, SearchCondition searchCondition) {
        return customFishBreadRepository.searchByCondition(uuid, searchCondition, pageable);
    }

    @Override
    @Transactional
    public void deleteFishBread(Long fishId, String userId) {
        FishBread fishBread = fishBreadRepository.findByIdAndReceiverUid(fishId, userId)
                .orElseThrow(CannotFindFishBread::new);
        if(fishBread.getStatus() == FishBreadStatus.DELETED) {
            throw new AlreadyDeletedFishBread();
        }
        fishBread.delete();
    }

    @Override
    public MainInfoResDto getMainInfo(String cartUUid, UserToken userToken) {
        int maxCount = 6;
        if(userToken != null) {
            if(cartUUid.equals(userToken.getUserId())) {
                maxCount = 100;
            }
        }

        User user = userRepository.findByUserUid(cartUUid).orElseThrow(CannotFindUser::new);

        return MainInfoResDto.builder()
                .nickname(user.getDisplayName())
                .totalCount(customFishBreadRepository.findFishBreadCountAll(cartUUid))
                .unreadCount(customFishBreadRepository.findFishBreadCountUnRead(cartUUid, maxCount))
                .build();
    }
}
