package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import java.util.List;

/**
 * 이번 주 외박 신청 내역 조회 UseCase
 */
public interface GetRecentSleepoversUseCase {
    /**
     * 이번 주에 신청된 외박 내역을 조회합니다.
     * 조회 범위: 이번 주 토요일 00:00 ~ 일요일 18:00
     * 
     * <p>현재 시점이 토요일/일요일이면 이번 주말,
     * 평일이면 직전 주말의 신청 내역을 반환합니다.</p>
     * 
     * @return 이번 주(또는 직전 주) 외박 신청 목록
     */
    List<Sleepover> getRecentSleepovers();
}
