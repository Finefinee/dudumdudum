package com.finefinee.dudumdudum.application.port.out;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SleepoverRepository {
    void save(Sleepover sleepover);
    List<Sleepover> findAll();
    Optional<Sleepover> findById(UUID id);
    java.util.List<Sleepover> findByMemberId(java.util.UUID memberId);
    List<Sleepover> findByAppliedAtBetween(LocalDateTime start, LocalDateTime end);
}
