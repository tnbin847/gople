package com.bins.gople.global.config.secure;

/**
 * 스프링 시큐리티 처리를 위한 상수들을 정의해놓은 상수 클래스
 *
 * @author PARK SU BIN
 * @version v.1.0
 */


public final class SecurityConstants {
    /**
     * 인증 요구없이 접근을 허용할 요청 URL들을 정의한 상수 배열
     */
    public static final String[] PUBLICY_REQUEST_URL_MATCHERS = {
            "/",
            "/sign-up",
            "/api/v1/login",
            "/api/v1/logout",
            "/api/v1/user/account",
            "/api/v1/user/account/**"
    };

    /**
     * 일반 로그인 처리를 위한 아이디 파라미터 키
     */
    public static final String FORM_USERNAME_KEY = "userId";

    /**
     * 일반 로그인 처리를 위한 비밀번호 파라미터 키
     */
    public static final String FORM_PASSWORD_KEY = "password";
}