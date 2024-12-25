package com.bins.gople.domain.user.mapper;

import com.bins.gople.domain.user.dto.SignUpRequest;
import com.bins.gople.domain.user.dto.UserRoleRequest;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    /**
     * 요청한 아이디의 존재 여부를 확인한다.
     */
    boolean existsById(String id);

    /**
     * 요청한 이메일의 존재 여부를 확인한다.
     */
    boolean existsByEmail(String email);

    /**
     * 가입 정보를 저장한다.
     */
    int insertUser(SignUpRequest signUpDto);

    /**
     * 가입된 사용자의 번호를 토대로 권한 정보를 저장한다.
     */
    void insertUserRole(UserRoleRequest userRoleRegisterDto);
}