package com.finefinee.dudumdudum.infra.in.web.controller;

import com.finefinee.dudumdudum.application.port.in.ApproveTeacherUseCase;
import com.finefinee.dudumdudum.application.port.in.GetPendingTeachersUseCase;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.infra.in.web.dto.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final GetPendingTeachersUseCase getPendingTeachersUseCase;
    private final ApproveTeacherUseCase approveTeacherUseCase;

    @GetMapping("/teachers/pending")
    public ApiResponse<List<Member>> getPendingTeachers() {
        return ApiResponse.success(getPendingTeachersUseCase.getPendingTeachers());
    }

    @PostMapping("/teachers/{memberId}/approve")
    public ApiResponse<Void> approveTeacher(@PathVariable UUID memberId) {
        approveTeacherUseCase.approveTeacher(memberId);
        return ApiResponse.success(null);
    }
}
