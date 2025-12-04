package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.application.port.out.SleepoverRepository;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SleepoverPersistenceAdapter implements SleepoverRepository {

    private final SleepoverJpaRepository sleepoverJpaRepository;

    @Override
    public void save(Sleepover sleepover) {
        sleepoverJpaRepository.save(sleepover);
    }

    @Override
    public List<Sleepover> findAll() {
        return sleepoverJpaRepository.findAll();
    }

    @Override
    public Optional<Sleepover> findById(UUID id) {
        return sleepoverJpaRepository.findById(id);
    }

    @Override
    public List<Sleepover> findByMemberId(java.util.UUID memberId) {
        return sleepoverJpaRepository.findByMemberId(memberId);
    }

    @Override
    public List<Sleepover> findByAppliedAtBetween(LocalDateTime start, LocalDateTime end) {
        return sleepoverJpaRepository.findByAppliedAtBetween(start, end);
    }
}
