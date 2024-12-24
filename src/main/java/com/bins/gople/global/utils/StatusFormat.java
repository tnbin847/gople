package com.bins.gople.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 데이터베이스 테이블 내 존재하는 상태 여부 컬럼을 처리하기 위해 타입이 다른 값들을 상응되는 의미별로 정의한 {@link Enum}클래스
 *
 * @author  PARK SU BIN
 * @version v.1.0
 */


@RequiredArgsConstructor
@Getter
public enum StatusFormat {
    YES (1, "Y", true),
    NO (0, "N", false);

    private final int number;
    private final String symbol;
    private final boolean bool;

    /**
     * {@code 1}또는 {@code 0}의 {@code int}타입의 인자값에 해당되는 {@code boolean}타입의 상태값을 반환한다.
     */
    public static boolean numberToBoolean(int number) {
        return Arrays.stream(values())
                .filter(format -> format.getNumber() == number)
                .findFirst()
                .map(StatusFormat::isBool)
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert '" + number + "' to boolean type!"));
    }
}