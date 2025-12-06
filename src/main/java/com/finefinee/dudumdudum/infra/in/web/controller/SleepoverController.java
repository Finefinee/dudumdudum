package com.finefinee.dudumdudum.infra.in.web.controller;

import com.finefinee.dudumdudum.application.port.in.ApplySleepoverUseCase;
import com.finefinee.dudumdudum.application.port.in.ReadSleepoverUseCase;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import com.finefinee.dudumdudum.infra.config.security.CustomUserDetails;
import com.finefinee.dudumdudum.infra.in.web.dto.common.ApiResponse;
import com.finefinee.dudumdudum.infra.in.web.dto.response.SleepoverResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sleepover")
@RequiredArgsConstructor
public class SleepoverController {

    private final ApplySleepoverUseCase applySleepoverUseCase;
    private final ReadSleepoverUseCase readSleepoverUseCase;

    @PostMapping("/apply")
    public ApiResponse<Void> applySleepover(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        applySleepoverUseCase.applySleepover(userDetails.getMember().getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/my")
    public ApiResponse<List<SleepoverResponse>> getMySleepovers(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<Sleepover> sleepovers = readSleepoverUseCase.getMySleepovers(userDetails.getMember().getId());
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return ApiResponse.success(responses);
    }

    @GetMapping("/all")
    public ApiResponse<List<SleepoverResponse>> getAllSleepovers() {
        List<Sleepover> sleepovers = readSleepoverUseCase.getAllSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return ApiResponse.success(responses);
    }

    @GetMapping("/recent")
    public ApiResponse<List<SleepoverResponse>> getRecentSleepovers() {
        List<Sleepover> sleepovers = readSleepoverUseCase.getRecentSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());

        return ApiResponse.success(responses);
    }
}
