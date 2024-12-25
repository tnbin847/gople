package com.bins.gople.domain.user.dto;

import com.bins.gople.domain.user.dto.enums.LoginType;
import com.bins.gople.global.utils.StatusFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequest {

    private Long userId;

    private String id;

    @Setter
    private String password;

    private String passwordConfirm;

    private String name;

    private String email;

    @Setter
    private LoginType loginType;

    private final int enabled = StatusFormat.YES.getNumeric();

    private final String useYn = StatusFormat.YES.getSymbol();

    private final String deleteYn = StatusFormat.NO.getSymbol();
}