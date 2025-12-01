package com.finefinee.dudumdudum.infra.in.web.dto.response;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SleepoverResponse {
    private UUID id;
    private String memberName;
    private LocalDateTime appliedAt;

    public static SleepoverResponse from(Sleepover sleepover) {
        return SleepoverResponse.builder()
                .id(sleepover.getId())
                .memberName(sleepover.getMember().getName())
                .appliedAt(sleepover.getAppliedAt())
                .build();
    }
}
