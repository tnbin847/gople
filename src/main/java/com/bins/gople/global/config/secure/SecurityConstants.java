package com.bins.gople.global.config.secure;

/**
 * 스프링 시큐리티 처리를 위한 상수들을 정의한 상수 클래스
 */

public final class SecurityConstants {

    /**
     * 인증 요구 없이 접근 가능한 요청 URL들을 정의한 상수 배열
     */
    public static final String[] PUBLICY_REQUEST_URL_MATCHERS = {
            "/",
            "/sign-up",
            "/api/v1/login",
            "/api/v1/logout",
            "/api/v1/user",
            "/api/v1/user/exists/**"
    };
}