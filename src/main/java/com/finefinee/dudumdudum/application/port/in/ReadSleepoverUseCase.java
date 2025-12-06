package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;

import java.util.List;
import java.util.UUID;

public interface ReadSleepoverUseCase {
    List<Sleepover> getAllSleepovers();
    List<Sleepover> getMySleepovers(UUID memberId);
    List<Sleepover> getRecentSleepovers();
}
