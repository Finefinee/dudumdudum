package com.finefinee.dudumdudum.infra.in.web.controller;

import com.finefinee.dudumdudum.application.port.in.SleepoverUseCase;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import com.finefinee.dudumdudum.infra.config.security.CustomUserDetails;
import com.finefinee.dudumdudum.infra.in.web.dto.common.ApiResponse;
import com.finefinee.dudumdudum.infra.in.web.dto.request.SleepoverRequest;
import com.finefinee.dudumdudum.infra.in.web.dto.response.SleepoverResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sleepover")
@RequiredArgsConstructor
public class SleepoverController {

    private final SleepoverUseCase sleepoverUseCase;

    @PostMapping("/apply")
    public ApiResponse<Void> applySleepover(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        sleepoverUseCase.applySleepover(userDetails.getMember().getId());
        return new ApiResponse<>(200, "외박 신청 성공", null);
    }

    @GetMapping("/my")
    public ApiResponse<List<SleepoverResponse>> getMySleepovers(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<Sleepover> sleepovers = sleepoverUseCase.getMySleepovers(userDetails.getMember().getId());
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return new ApiResponse<>(200, "내 외박 신청 조회 성공", responses);
    }

    @GetMapping("/all")
    public ApiResponse<List<SleepoverResponse>> getAllSleepovers() {
        List<Sleepover> sleepovers = sleepoverUseCase.getAllSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return new ApiResponse<>(200, "전체 외박 신청 조회 성공", responses);
    }

    @GetMapping("/recent")
    public ApiResponse<List<SleepoverResponse>> getRecentSleepovers() {
        List<Sleepover> sleepovers = sleepoverUseCase.getRecentSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());

        return new ApiResponse<>(200, "최근 외박 신청 조회 성공", responses);
    }
}
