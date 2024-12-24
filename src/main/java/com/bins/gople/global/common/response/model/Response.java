package com.bins.gople.global.common.response.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 성공 응답과 에러 및 실패 응답 모두 공통적으로 응답 바디부에서 포함할 필수 데이터들을 정의한 클래스
 *
 * @author PARK SU BIN
 * @version v.1.0
 */


@Getter
public class Response {

    /**
     * 응답 시간 및 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime responseAt = LocalDateTime.now();

    /**
     * 요청 처리에 따른 응답 상태를 나타내는 문자열 값
     * <p>
     * - 성공 응답 : "success" || 실패 응답 : "fail" || 에러 응답 : "error"
     */
    private final String status;

    /**
     * 요청 처리 상태를 나타내는 응답 코드
     * <p>
     * - {@link com.bins.gople.global.common.response.ResponseEnum}에 정의된 {@code code}값 저장된다.
     */
    private final int code;

    /**
     * 응답 메시지
     * <p>
     * - {@link com.bins.gople.global.common.response.ResponseEnum}에 정의된 {@code message}가 저장된다.
     */
    private final String message;

    public Response(String status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * 응답 바디부의 필수 데이터인 {@code status}에 담길 값들을 정의한 {@link Enum}클래스
     */
    @Getter
    public enum ResponseType {
        SUCCESS, FAIL, ERROR;

        public String getStatusType() {
            return name().toLowerCase();
        }
    }
}