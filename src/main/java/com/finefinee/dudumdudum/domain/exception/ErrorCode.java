package com.finefinee.dudumdudum.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "지원하지 않는 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "엔티티를 찾을 수 없습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "잘못된 타입입니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "회원을 찾을 수 없습니다."),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 이메일입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "M003", "로그인에 실패했습니다."),
    ADMIN_SIGNUP_NOT_ALLOWED(HttpStatus.FORBIDDEN, "M004", "관리자 권한으로 회원가입할 수 없습니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "M005", "잘못된 권한입니다."),
    MEMBER_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "M006", "이미 존재하는 아이디입니다."),

    // Sleepover
    SLEEPOVER_DATE_INVALID(HttpStatus.BAD_REQUEST, "S001", "외박 신청은 토요일 ~ 일요일 오후 6시까지만 가능합니다."),
    SLEEPOVER_NOT_FOUND(HttpStatus.NOT_FOUND, "S002", "외박 신청 정보를 찾을 수 없습니다."),
    INVALID_SLEEPOVER_TIME(HttpStatus.BAD_REQUEST, "S003", "잘못된 외박 신청 시간입니다."),
    DUPLICATE_SLEEPOVER_APPLICATION(HttpStatus.BAD_REQUEST, "S004", "이미 이번 주에 외박 신청을 하셨습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
