package com.nyanggle.nyangmail.controller;

import com.nyanggle.nyangmail.config.AuthUser;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;
import com.nyanggle.nyangmail.oauth.UserPrincipal;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import com.nyanggle.nyangmail.service.FishBreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class FishBreadController {
    private final FishBreadService fishBreadService;

    @PostMapping("/{uuid}")
    public ResponseEntity createFishBread(@RequestBody @Valid FishBreadCreateReqDto reqDto,
                                          @PathVariable(value = "uuid") String receiverUid) {
        fishBreadService.create(reqDto,receiverUid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 붕어빵 목록
     * @param pageable
     * @param searchCondition
     * @param principal
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<FishBreadListResDto>> findFishBreadAll(Pageable pageable,
                                                @ModelAttribute SearchCondition searchCondition,
                                                @AuthUser @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(fishBreadService.findBySearchCondition(principal.getUserId(), pageable, searchCondition));
    }

    /**
     * 붕어빵 상세
     * Todo 사용자 토큰을 이용한 검증 필요.
     * @param fishId
     * @return
     */
    @GetMapping("/{fishId}")
    public ResponseEntity findFishBreadByUUID(@PathVariable(name = "fishId") Long fishId,
                                              @AuthUser @AuthenticationPrincipal UserToken userToken) {
        FishBreadResDto resDto = fishBreadService.findByFishUid(userToken.getUserId(), fishId);
        fishBreadService.fishBreadstatusChange(fishId);
        return ResponseEntity.ok(resDto);
    }
}
