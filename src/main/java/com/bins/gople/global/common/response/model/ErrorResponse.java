package com.bins.gople.global.common.response.model;


import com.bins.gople.global.common.response.ResponseEnum;
import com.bins.gople.global.error.ValidationErrors;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 실패 또는 에러 응답의 구조를 정의한 클래스
 */

@Getter
public class ErrorResponse extends Response {
    private List<ValidationErrors> errors;

    @Builder
    private ErrorResponse(int code, String message, List<ValidationErrors> errors) {
        super(ResponseType.FAIL.getStatusType(), code, message);
        this.errors = errors == null ? Collections.emptyList() : errors;
    }

    @Builder
    private ErrorResponse(int code, String message) {
        super(ResponseType.ERROR.getStatusType(), code, message);
    }

    /**
     * 클라이언트의 잘못된 요청으로 인해 발생된 필드 에러로 인한 실패 응답을 반환한다.
     * <p>
     * <blockquote><pre>
     *     {
     *         responseAt : 2024-12-24 20:16:42,
     *         status : "fail",
     *         code : -800100,
     *         message: "에러 응답 메세지",
     *         errors: [ ]
     *     }
     * </pre></blockquote>
     *
     * @param responseEnum  에러 응답 코드와 관련된 정보들을 정의한 {@link Enum}클래스
     * @param errors        에러 정보 리스트
     * @return              응답 객체
     */
    public static ErrorResponse fail(final ResponseEnum responseEnum, final List<ValidationErrors> errors) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .errors(errors)
                .build();
    }

    /**
     * 예외 발생으로 인한 에러 응답을 반환한다.
     * <p>
     * <blockquote><pre>
     *     {
     *         responseAt : 2024-12-24 20:16:42,
     *         status : "error",
     *         code : -900000,
     *         message: "에러 응답 메세지"
     *     }
     * </pre></blockquote>
     *
     * @param responseEnum  에러 응답 코드와 관련된 정보들을 정의한 {@link Enum}클래스
     * @return              응답 객체
     */
    public static ErrorResponse error(final ResponseEnum responseEnum) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .build();
    }
}