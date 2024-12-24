package com.bins.gople.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}을 구현한 {@link Enum}클래스에 정의된 코드값을 데이터베이스에 저장하거나, 데이터베이스로부터 가져온 코드값을 알맞은 {@link Enum}으로
 * 변환하는 역할을 수행하는 커스텀 타입 핸들러 클래스
 *
 * @author PARK SU BIN
 * @version v.1.0
 */


@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {
    /**
     * 타입 핸들러의 처리 대상인 {@link Enum}클래스
     */
    private final Class<E> type;

    /**
     * {@link Enum}클래스에 정의된 상수들로 이루어진 배열
     */
    private final E[] constants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        }
        this.type = type;
        this.constants = type.getEnumConstants();
        if (!type.isInterface() && this.constants == null) {
            throw new IllegalArgumentException("'" + type.getSimpleName() + "' does not represent an enum type!");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertColumnToCodeEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertColumnToCodeEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertColumnToCodeEnum(cs.getString(columnIndex));
    }

    /**
     * {@link Enum}클래스 내의 상수값들 중 데이터베이스로부터 가져온 코드값과 일치한 상수값이 존재할 경우, 해당 {@link Enum}을 반환한다.
     *
     * @param columnCode    데이터베이스로부터 가져온 코드값
     * @return {@code E}    {@link Enum}
     */
    private E convertColumnToCodeEnum(String columnCode) {
        try {
            for (E codeEnum : constants) {
                if (codeEnum.getCode().equals(columnCode)) {
                    return codeEnum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert '" + columnCode + "' to '" + type.getSimpleName() + "'!");
        }
    }
}