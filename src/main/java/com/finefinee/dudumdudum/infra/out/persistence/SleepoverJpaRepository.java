package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SleepoverJpaRepository extends JpaRepository<Sleepover, UUID> {
    java.util.List<Sleepover> findByMemberId(java.util.UUID memberId);
    List<Sleepover> findByAppliedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Sleepover> findByMemberIdAndAppliedAtBetween(UUID memberId, LocalDateTime start, LocalDateTime end);
}
