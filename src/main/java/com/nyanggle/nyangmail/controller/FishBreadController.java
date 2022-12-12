package com.nyanggle.nyangmail.controller;

import com.nyanggle.nyangmail.config.AuthUser;
import com.nyanggle.nyangmail.exception.user.UnAuthorizedException;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import com.nyanggle.nyangmail.persistence.entity.User;
import com.nyanggle.nyangmail.service.FishBreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fishbread")
@RequiredArgsConstructor
@Slf4j
public class FishBreadController {
    private final FishBreadService fishBreadService;

    @PostMapping("/{uuid}")
    public ResponseEntity createFishBread(@RequestBody @Valid FishBreadCreateReqDto reqDto,
                                          @PathVariable(value = "uuid") String receiverUid) {
        fishBreadService.create(reqDto, receiverUid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 카트에 들어갔을 때 가져오는
     * 전체 붕어빵 갯수,
     * 읽지 않은 붕어빵 갯수
     * 닉네임
     * @param userToken
     * @param cartUUid
     * @return
     */
    @GetMapping("/{uuid}")
    public ResponseEntity getMainInfo(@AuthenticationPrincipal UserToken userToken,
                                      @PathVariable(value = "uuid") String cartUUid) {
        return ResponseEntity.ok(fishBreadService.getMainInfo(cartUUid, userToken));
    }


    /**
     * 붕어빵 목록
     * @param pageable
     * @param searchCondition
     * @param userToken
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<FishBreadListResDto>> findFishBreadAll(Pageable pageable,
                                                @ModelAttribute SearchCondition searchCondition,
                                                @AuthUser @AuthenticationPrincipal UserToken userToken) {
        return ResponseEntity.ok(fishBreadService.findBySearchCondition(userToken.getUserId(), pageable, searchCondition));
    }

    /**
     * 붕어빵 상세
     *
     * @param fishId
     * @return
     */
    @GetMapping("/{uuid}/{fishId}")
    public ResponseEntity<FishBreadResDto> findFishBreadByUUID(@PathVariable(name = "fishId") Long fishId,
                                                               @PathVariable(name = "uuid") String cartId,
                                              @AuthUser @AuthenticationPrincipal UserToken userToken) {
        if(!cartId.equals(userToken.getUserId())) {
            throw new UnAuthorizedException();
        }
        FishBreadResDto resDto = fishBreadService.findByFishUid(cartId, fishId);
        fishBreadService.fishBreadstatusChange(fishId);
        return ResponseEntity.ok(resDto);
    }

    @DeleteMapping("/{fishId}")
    public void deleteFishBread(@PathVariable(name = "fishId") Long fishId,
                                @AuthUser @AuthenticationPrincipal UserToken userToken){
        fishBreadService.deleteFishBread(fishId, userToken.getUserId());
    }
}
