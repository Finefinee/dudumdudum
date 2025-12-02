package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;

public interface GetAllSleepoversUseCase {
    List<Sleepover> getAllSleepovers();
}
