package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;

/**
 * 외박 신청 전체 조회 UseCase
 */
public interface GetAllSleepoversUseCase {
    /**
     * 시스템에 등록된 모든 외박 신청 내역을 조회합니다.
     * 
     * @return 전체 외박 신청 목록 (시간 제한 없음)
     */
    List<Sleepover> getAllSleepovers();
}
