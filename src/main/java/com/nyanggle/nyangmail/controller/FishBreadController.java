package com.nyanggle.nyangmail.controller;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadCreateReqDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadResDto;
import com.nyanggle.nyangmail.service.FishBreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/fish")
@RequiredArgsConstructor
public class FishBreadController {
    private final FishBreadService fishBreadService;

    /**
     * 한개 가져오기
     * Todo 사용자 토큰을 이용한 검증 필요.
     * @param fishId
     * @return
     */
    @GetMapping("/{fishId}")
    public ResponseEntity findFishBreadByUUID(@PathVariable(name = "fishId") Long fishId) {
        String uUid = "U18414f5037a0001"; // @AuthenticationPrincipal 추가한 후 uUid 받아와서 넣어주기.
        FishBreadResDto resDto = fishBreadService.findByFishUid(uUid, fishId);
        fishBreadService.fishBreadstatusChange(fishId);
        return ResponseEntity.ok(resDto);
    }

    @PostMapping("/{uuid}")
    public ResponseEntity createFishBread(@RequestBody @Valid FishBreadCreateReqDto reqDto, @PathVariable(value = "uuid") String receiverUid) {
        fishBreadService.create(reqDto,receiverUid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
