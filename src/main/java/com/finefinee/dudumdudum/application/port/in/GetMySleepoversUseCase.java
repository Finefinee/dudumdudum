package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;
import java.util.UUID;

/**
 * 특정 회원의 외박 신청 내역 조회 UseCase
 */
public interface GetMySleepoversUseCase {
    /**
     * 특정 회원이 신청한 모든 외박 내역을 조회합니다.
     * 
     * @param memberId 조회할 회원의 ID
     * @return 해당 회원의 전체 외박 신청 목록 (시간 제한 없음)
     */
    List<Sleepover> getMySleepovers(UUID memberId);
}
