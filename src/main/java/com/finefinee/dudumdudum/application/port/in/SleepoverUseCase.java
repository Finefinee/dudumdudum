package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;

public interface SleepoverUseCase {
    void applySleepover(java.util.UUID memberId);
    List<Sleepover> getAllSleepovers();
    List<Sleepover> getMySleepovers(java.util.UUID memberId);
    List<Sleepover> getRecentSleepovers();
}
