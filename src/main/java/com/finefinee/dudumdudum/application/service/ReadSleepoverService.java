package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.ReadSleepoverUseCase;
import com.finefinee.dudumdudum.application.port.out.SleepoverRepository;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadSleepoverService implements ReadSleepoverUseCase {

    private final SleepoverRepository sleepoverRepository;

    @Override
    public List<Sleepover> getAllSleepovers() {
        return sleepoverRepository.findAll();
    }

    @Override
    public List<Sleepover> getMySleepovers(UUID memberId) {
        return sleepoverRepository.findByMemberId(memberId);
    }

    @Override
    public List<Sleepover> getRecentSleepovers() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.SATURDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        if (now.getDayOfWeek() != DayOfWeek.SATURDAY && now.getDayOfWeek() != DayOfWeek.SUNDAY) {
             startOfWeek = now.with(java.time.temporal.TemporalAdjusters.previous(DayOfWeek.SATURDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (now.getDayOfWeek() == DayOfWeek.SUNDAY) {
             startOfWeek = now.minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        
        LocalDateTime endOfWeek = startOfWeek.plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);

        return sleepoverRepository.findByAppliedAtBetween(startOfWeek, endOfWeek);
    }
}
