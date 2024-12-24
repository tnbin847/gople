package com.bins.gople.global.error;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 클라이언트의 요청에 의해 발생된 유효성 검증 에러를 처리 및 그 정보를 리스트로 반환하는 역할을 수행하는 클래스
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ValidationErrors {

    /**
     * 에러가 발생된 필드명
     */
    private final String field;
    /**
     * 필드에 담긴 사용자 입력 값
     */
    private final String value;
    /**
     * 에러가 발생된 원인
     */
    private final String message;

    /**
     * 특정 필드에 의해 발생된 에러에 대한 정보를 반환한다.
     *
     * @return  에러 정보 리스트
     */
    public static List<ValidationErrors> of(final String field, final String value, final String message) {
        final List<ValidationErrors> errors = new ArrayList<>();
        errors.add(new ValidationErrors(field, value, message));
        return errors;
    }

    /**
     * 요청 파라미터의 유효성 검증 실패로 인해 발생된 에러에 대한 정보를 반환한다.
     * <p>
     * {@code @Valid}와 함께 {@code @ResponseBody} 또는 {@code @ModelAttribute} 사용시 발생되는
     * {@code MethodArgumentNotValidException} 또는 {@code BindException} 처리 시 호출된다.
     *
     * @param bindingResult 스프링에서 제공하는 폼 데이터 바인딩 과정 중에 발생되는 검증 오류를 수집 및 처리하는 객체
     * @return  에러 정보 리스트
     */
    public static List<ValidationErrors> of(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(fieldError -> new ValidationErrors(
                fieldError.getField(),
                nullSafeToString(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 요청 파라미터의 유효성 검증 실패로 인해 발생된 에러에 대한 정보를 반환한다.
     * <p>
     * {@code Validated}를 사용함으로 인해 파라미터가 제약 조건 위반으로 발생된 {@code ConstraintViolationException}을 처리할 때 호출된다.
     *
     * @param violations    검증 실패 원인에 대한 정보를 담고 있는 객체
     * @return              에러 정보 리스트
     */
    public static List<ValidationErrors> of(final Set<ConstraintViolation<?>> violations) {
        final List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(violations);
        return constraintViolations.stream().map(constraintViolation -> new ValidationErrors(
                parsingFrom(constraintViolation.getPropertyPath().toString()),
                nullSafeToString(constraintViolation.getInvalidValue()),
                constraintViolation.getMessageTemplate()
        )).collect(Collectors.toList());
    }

    /**
     * 에러가 발생된 속성의 경로에서 '.' 기준으로 속성명을 반환하되, 경로에 '.'이 없을 경우 경로 그 자체를 반환한다.
     *
     * @param propertyPath  검증 에러가 발생된 속성 경로
     * @return              속성명 또는 속성 경로
     */
    private static String parsingFrom(String propertyPath) {
        int lastIndex = propertyPath.lastIndexOf('.');
        return lastIndex == -1 ? propertyPath : propertyPath.substring(lastIndex);
    }

    /**
     * 전달된 값의 {@code null}여부에 따른 빈 문자열 또는 문자열로 변환된 값을 반환한다.
     *
     * @param value 에러가 발생된 필드에 저장된 값
     * @return      빈 문자열 또는 문자열로 변환된 값
     */
    private static String nullSafeToString(Object value) {
        return value == null ? "" : value.toString();
    }
}