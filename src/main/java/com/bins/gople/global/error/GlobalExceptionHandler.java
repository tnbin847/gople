package com.bins.gople.global.error;


import com.bins.gople.global.common.response.ResponseEnum;
import com.bins.gople.global.common.response.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * 전역 예외 처리 클래스
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception e) {
        LOGGER.error("Exception occurred...!\n{}", parsingStackTrace(e));
        final ResponseEnum responseEnum = ResponseEnum.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(responseEnum.getStatus()).body(ErrorResponse.error(responseEnum));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleRequestValidException(BindException e) {
        final ResponseEnum responseEnum = ResponseEnum.INVALID_PARAM_VALUES;
        return ResponseEntity.status(responseEnum.getStatus()).body(ErrorResponse.fail(responseEnum, ValidationErrors.of(e.getBindingResult())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        final ResponseEnum responseEnum = ResponseEnum.INVALID_PARAM_VALUES;
        return ResponseEntity.status(responseEnum.getStatus()).body(ErrorResponse.fail(responseEnum, ValidationErrors.of(e.getConstraintViolations())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String field = e.getName();
        String value = e.getValue() == null ? "" : e.getValue().toString();
        String message = field + "의 타입은 " + e.getRequiredType().getName() + " 타입이어야 합니다.";
        final ResponseEnum responseEnum = ResponseEnum.INVALID_PARAM_TYPE;
        return ResponseEntity.status(responseEnum.getStatus()).body(ErrorResponse.fail(responseEnum, ValidationErrors.of(field, value, message)));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final StringBuilder builder = new StringBuilder();
        String message = builder.append(e.getMethod()).append(" method is not supported for this request.").toString();
        LOGGER.error("HttpRequestMethodNotSupportedException occurred...!\n{}\n{}", parsingStackTrace(e), message);
        final ResponseEnum responseEnum = ResponseEnum.NOT_SUPPORTED_METHOD;
        return ResponseEntity.status(responseEnum.getStatus()).body(ErrorResponse.fail(responseEnum, null));
    }

    private String parsingStackTrace(Exception e) {
        final StackTraceElement[] traceElements = e.getStackTrace();
        final StringBuffer buffer = new StringBuffer();
        buffer.append(traceElements[0].getClassName()).append(" : ").append(traceElements[0].getLineNumber()).append(" Line, in ")
              .append(traceElements[0].getMethodName()).append("() - ").append(e.getMessage());
        return buffer.toString();
    }
}