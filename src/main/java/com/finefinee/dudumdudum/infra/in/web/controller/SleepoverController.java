package com.finefinee.dudumdudum.infra.in.web.controller;

import com.finefinee.dudumdudum.application.port.in.ApplySleepoverUseCase;
import com.finefinee.dudumdudum.application.port.in.GetAllSleepoversUseCase;
import com.finefinee.dudumdudum.application.port.in.GetMySleepoversUseCase;
import com.finefinee.dudumdudum.application.port.in.GetRecentSleepoversUseCase;
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

    private final ApplySleepoverUseCase applySleepoverUseCase;
    private final GetAllSleepoversUseCase getAllSleepoversUseCase;
    private final GetMySleepoversUseCase getMySleepoversUseCase;
    private final GetRecentSleepoversUseCase getRecentSleepoversUseCase;

    @PostMapping("/apply")
    public ApiResponse<Void> applySleepover(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        applySleepoverUseCase.applySleepover(userDetails.getMember().getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/my")
    public ApiResponse<List<SleepoverResponse>> getMySleepovers(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<Sleepover> sleepovers = getMySleepoversUseCase.getMySleepovers(userDetails.getMember().getId());
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return ApiResponse.success(responses);
    }

    @GetMapping("/all")
    public ApiResponse<List<SleepoverResponse>> getAllSleepovers() {
        List<Sleepover> sleepovers = getAllSleepoversUseCase.getAllSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());
        
        return ApiResponse.success(responses);
    }

    @GetMapping("/recent")
    public ApiResponse<List<SleepoverResponse>> getRecentSleepovers() {
        List<Sleepover> sleepovers = getRecentSleepoversUseCase.getRecentSleepovers();
        List<SleepoverResponse> responses = sleepovers.stream()
                .map(SleepoverResponse::from)
                .collect(Collectors.toList());

        return ApiResponse.success(responses);
    }
}
