package com.bins.gople.global.common.response.model;

import com.bins.gople.global.common.response.ResponseEnum;
import lombok.Builder;
import lombok.Getter;

/**
 * 성공 응답 클래스
 *
 * @author PARK SU BIN
 * @version v.1.0
 */


@Getter
public class ApiResponse<T> extends Response {

    /**
     * 요청 처리후 결과 데이터
     */
    private final T data;

    @Builder
    private ApiResponse(int code, String message, T data) {
        super(ResponseType.SUCCESS.getStatusType(), code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     * <p>
     * <blockquote><pre>
     *     {
     *         responseAt : 2024-12-24 20:16:42,
     *         status : "success",
     *         code : 100000,
     *         message: "성공 응답 메세지",
     *         data: { }
     *     }
     * </pre></blockquote>
     *
     * @param responseEnum  성공 응답 코드와 관련된 정보들을 정의한 {@link Enum}클래스
     * @param data          결과 데이터 - {@code null}일 경우, {@code 빈 객체({})} 또는 {@code 빈 배열([])} 등을 담는다.
     * @return              응답 객체
     * @param <T>           결과 데이터의 타입
     */
    public static<T> ApiResponse<T> ok(final ResponseEnum responseEnum, final T data) {
        return ApiResponse.<T>builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .data(data)
                .build();
    }
}