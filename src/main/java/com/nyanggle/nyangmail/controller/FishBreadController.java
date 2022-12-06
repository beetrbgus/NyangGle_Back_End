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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/fishbread")
@RequiredArgsConstructor
@Slf4j
public class FishBreadController {
    private final FishBreadService fishBreadService;
    
    @PostMapping("/{uuid}")
    public ResponseEntity createFishBread(HttpServletRequest request,
                                          @RequestBody @Valid FishBreadCreateReqDto reqDto,
                                          @PathVariable(value = "uuid") String receiverUid) {
        String senderIp = request.getHeader("X-Forwarded-For");
        if(senderIp == null) {
            senderIp = "";
        }
        fishBreadService.create(reqDto, receiverUid, senderIp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 붕어빵 목록
     * @param pageable
     * @param searchCondition
     * @param userToken
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Page<FishBreadListResDto>> findFishBreadAll(Pageable pageable,
                                                @ModelAttribute SearchCondition searchCondition,
                                                @AuthUser @AuthenticationPrincipal UserToken userToken) {
        return ResponseEntity.ok(fishBreadService.findBySearchCondition(userToken.getUserId(), pageable, searchCondition));
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

    @GetMapping("/count/{uuid}")
    public ResponseEntity<Long> findFishBreadCount(@PathVariable(value = "uuid") String receiverUid) {
        return ResponseEntity.ok(fishBreadService.findFishBreadCount(receiverUid));
    }
}
