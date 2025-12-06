package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.member.Member;

import java.util.List;

public interface GetPendingTeachersUseCase {
    List<Member> getPendingTeachers();
}
