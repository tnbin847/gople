package com.bins.gople.global.common.mybatis;

/**
 * 데이터베이스에 저장하고자 하는 {@code code}값을 정의한 {@link Enum}클래스를 일관적으로 구현하기 위한 인터페이스
 */


public interface CodeEnum {

    /**
     * 데이터베이스에 저장하거나 데이터베이스로부터 가져온 값을 알맞은 {@link Enum}으로 변환하기 위해 {@code code}값을 반환한다.
     */
    String getCode();

    /**
     * 뷰에 출력할 코드명을 반환한다.
     */
    String getLabel();
}