package com.bins.gople.global.common.response;


import com.bins.gople.global.common.response.model.ApiResponse;
import com.bins.gople.global.common.response.model.Response;
import com.bins.gople.global.error.GlobalExceptionHandler;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

/**
 * 성공 응답 반환시, 응답 바디부의 결과 데이터가 {@code null}일 경우, 빈 객체 또는 빈 배열을 그 결과 데이터에 담아 성공 응답을 반환기 위해 구현한 클래스
 */


@RestControllerAdvice (basePackages = "com.bins.gople.domain.**.controller", basePackageClasses = GlobalExceptionHandler.class)
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * {@link Response}나 그 하위 타입을 반환하지 않는 경우 {@code beforeBodyWrite()}메소드를 호출한다.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> type = returnType.getParameterType();
        if (ResponseEntity.class.isAssignableFrom(type)) {
            try {
                ParameterizedType parameterizedType = (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return !Response.class.isAssignableFrom(type);
    }

    /**
     * 응답 본문 처리시, {@link ApiResponse}의 {@code data}필드에 값이 {@code null}일 경우, 빈 객체 또는 빈 배열을 할당해 응답 객체를 생성한다.
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        final HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        if (body instanceof ApiResponse<?> apiResponse) {
            if (apiResponse.getData() == null) {
                apiResponse = ApiResponse.ok(resolveResponseEnum(servletResponse, request), generateDefaultCollection(returnType));
            }
            return apiResponse;
        }
        return body;
    }

    /**
     * HTTP 응답 상태와 메소드에 따라 적절한 {@link ResponseEnum}을 반환한다.
     *
     * @param response  클라이언트에게 전달할 응답 정보
     * @param request   HTTP 요청 정보
     * @return          {@code ResponseEnum}
     */
    private ResponseEnum resolveResponseEnum(HttpServletResponse response, ServerHttpRequest request) {
        return ResponseEnum.resolveEnum(HttpStatus.resolve(response.getStatus()), request.getMethodValue());
    }

    /**
     * 컬렉션을 활용하여 반환 타입이 {@code List}인지에 따라 빈 배열 또는 빈 객체를 반환한다.
     *
     * @param methodParam   Controller 클래스 내 메소드의 반환타입에 대한 정보를 담고 있는 객체
     * @return              빈 객체 또는 빈 배열
     */
    private Object generateDefaultCollection(MethodParameter returnType) {
        return returnType.getParameterType().isAssignableFrom(List.class) ? Collections.emptyList() : Collections.emptyMap();
    }
}