package com.bins.gople.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드(성공 또는 에러)와 관련된 정보 (응답 코드, HTTP 상태코드, 응답 메시지 등)를 정의 및 관리하기 위한 {@link Enum}클래스
 */


@RequiredArgsConstructor
@Getter
public enum ResponseEnum {
    /**
     * 성공 응답 코드
     */
    /* 공통 || ------------------------------------------------------------------------------------------------------- */
    OK (100000, HttpStatus.OK, "요청이 정상적으로 처리되었습니다."),
    CREATE (101100, HttpStatus.CREATED, "리소스가 생성되었습니다."),
    UPDATE (104200, HttpStatus.NO_CONTENT, "리소스가 변경되었습니다."),
    DELETE (104300, HttpStatus.NO_CONTENT, "리소스가 삭제되었습니다."),


    /**
     * 에러 응답 코드
     */
    /* 공통 || ------------------------------------------------------------------------------------------------------- */
    INVALID_PARAM_VALUES (-800100, HttpStatus.BAD_REQUEST, "요청 파리미터의 값이 유효하지 않습니다."),
    INVALID_PARAM_TYPE (-800101, HttpStatus.BAD_REQUEST, "잘못된 타입의 요청 파라미터입니다."),
    NOT_FOUND_RESOURCE (-804200, HttpStatus.NOT_FOUND, "요청하신 자원을 찾을 수 없습니다."),
    NOT_SUPPORTED_METHOD (-805200, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드 요청입니다."),
    INTERNAL_SERVER_ERROR (-900000, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버에 오류가 발생했습니다."),

    /* 사용자 || ------------------------------------------------------------------------------------------------------ */
    NOT_FOUND_USER (-804210, HttpStatus.NOT_FOUND, "요청하신 사용자를 찾을 수 없습니다.")
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;

    /**
     * {@code PUT}, {@code PATCH}, {@code DELETE} 요청 처리시, 해당 요청에 맞는 적절한 {@link ResponseEnum}값이 사용되었는지 판단한다.
     *
     * @param method        요청 HTTP 메소드 - {@code PUT}, {@code PATCH}, {@code DELETE}
     * @param responseEnum  특정 API 응답 코드에 해당되는 {@link Enum}클래스
     */
    private static boolean isNoContentMethod(HttpMethod method, ResponseEnum responseEnum) {
        switch (method) {
            case PUT, PATCH -> { return responseEnum == ResponseEnum.UPDATE; }
            case DELETE -> { return responseEnum == ResponseEnum.DELETE; }
            default -> { return false; }
        }
    }

    /**
     * {@link HttpStatus}와 요청 메소드명을 토대로 알맞은 {@link ResponseEnum}을 반환한다.
     */
    public static ResponseEnum resolveEnum(HttpStatus status, String method) {
        for (ResponseEnum responseEnum : values()) {
            if (responseEnum.getStatus() == status) {
                if (isNoContentMethod(HttpMethod.resolve(method), responseEnum)) {
                    return responseEnum;
                }
                return responseEnum;
            }
        }
        throw new IllegalArgumentException("No matching 'ResponseEnum found for '" + status + "'!");
    }
}