package com.yangjianzhou.dao.typeHandler;

import com.yangjianzhou.dao.enums.EnumValue;
import com.yangjianzhou.util.EnumUtils;

import java.sql.*;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public class EnumValueTypeHandler extends BaseTypeHandler {

    private Class type;

    public EnumValueTypeHandler(Class type) {
        if (!EnumValue.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException();
        }
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType) throws SQLException {
        if (null == parameter) {
            ps.setNull(i, Types.INTEGER);
        } else {
            ps.setInt(i, ((EnumValue) parameter).getValue());
        }

    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return EnumUtils.parseEnum(type, value);
        }
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return EnumUtils.parseEnum(type, value);
        }
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return EnumUtils.parseEnum(type, value);
        }
    }

    @Override
    public Object valueOf(String s) {

        Object result = null;
        int value = 0;

        if (null != s && 0 < s.length()) {
            try {
                value = Integer.parseInt(s);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        result = EnumUtils.parseEnum(type, value);
        return result;
    }
}
