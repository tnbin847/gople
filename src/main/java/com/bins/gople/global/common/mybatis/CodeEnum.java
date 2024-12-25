package com.bins.gople.global.common.mybatis;

/**
 * 데이터베이스에 저장하고자 하는 문자열 타입의 코드값을 정의한 {@link Enum}클래스를 일관적으로 구현하기 위해 정의한 인터페이스
 */

public interface CodeEnum {

    String getCode();

    String getLabel();
}