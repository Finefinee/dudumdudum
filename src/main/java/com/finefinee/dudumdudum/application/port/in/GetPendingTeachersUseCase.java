package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.infra.in.web.dto.response.MemberResponse;
import java.util.List;

public interface GetPendingTeachersUseCase {
    List<MemberResponse> getPendingTeachers();
}
