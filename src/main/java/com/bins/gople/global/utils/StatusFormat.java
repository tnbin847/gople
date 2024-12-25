package com.bins.gople.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 데이터베이스 테이블의 상태여부 컬럼에 대한 값을 처리하기 위해 타입이 다른 값들을 상응되는 의미별로 묶어 정의한 {@link Enum}클래스
 */

@RequiredArgsConstructor
@Getter
public enum StatusFormat {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int numeric;

    private final String symbol;

    private final boolean bool;
}