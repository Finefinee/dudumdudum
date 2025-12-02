package com.finefinee.dudumdudum.application.port.out;

import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SleepoverRepository {
    void save(Sleepover sleepover);
    List<Sleepover> findAll();
    Optional<Sleepover> findById(UUID id);
    java.util.List<Sleepover> findByMemberId(java.util.UUID memberId);
    List<Sleepover> findByAppliedAtBetween(LocalDateTime start, LocalDateTime end);
    
    default Sleepover findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SLEEPOVER_NOT_FOUND));
    }
}
