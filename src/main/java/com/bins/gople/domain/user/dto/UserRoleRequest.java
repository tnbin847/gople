package com.bins.gople.domain.user.dto;

import com.bins.gople.domain.user.dto.enums.Role;
import com.bins.gople.global.utils.StatusFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserRoleRequest {

    private Long userRoleId;

    private Long userId;

    private Role role;

    private String useYn;

    private String deleteYn;

    @Builder(builderClassName = "UserRoleRegisterBuilder", builderMethodName = "userRoleRegisterBuilder")
    public UserRoleRequest(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
        this.useYn = StatusFormat.YES.getSymbol();
        this.deleteYn = StatusFormat.NO.getSymbol();
    }
}