package com.bins.gople.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드와 관련된 정보를 정의 및 관리하기 위한 {@link Enum}클래스
 */

@RequiredArgsConstructor
@Getter
public enum ResponseEnum {

    /**
     * 공통 성공 응답 코드
     */
    OK (100000, HttpStatus.OK, "요청이 정상적으로 처리되었습니다."),
    CREATE (101100, HttpStatus.CREATED, "리소스가 생성되었습니다."),
    UPDATE (104200, HttpStatus.NO_CONTENT, "리소스가 변경되었습니다."),
    DELETE (104300, HttpStatus.NO_CONTENT, "리소스가 삭제되었습니다."),


    /**
     * 공통 에러 응답 코드
     */
    INVALID_REQUEST_PARAM_VALUES (-800100, HttpStatus.BAD_REQUEST, "요청 파라미터의 값이 유효하지 않습니다."),
    INVALID_REQUEST_PARAM_TYPE (-800101, HttpStatus.BAD_REQUEST, "요청 파라미터의 타입이 유효하지 않습니다."),
    NOT_FOUND_RESOURCE (-804200, HttpStatus.NOT_FOUND, "요청하신 자원을 찾을 수 없습니다."),
    NOT_SUPPORTED_METHOD (-805200, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드 방식의 요청입니다."),
    INTERNAL_SERVER_ERROR (-900000, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버에 오류가 발생했습니다."),

    /**
     * 사용자 에러 응답 코드
     */
    NOT_FOUND_USER (-804210, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    UNAUTHORIZED_USER (-801310, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    FORBIDDEN_USER (-803310, HttpStatus.FORBIDDEN, "요청에 대한 접근 권한이 없는 사용자입니다.")
    ;

    private final int code;

    private final HttpStatus status;

    private final String message;
}