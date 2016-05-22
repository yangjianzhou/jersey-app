package com.yangjianzhou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjianzhou on 16-4-11.
 */
public class SqlMapConditionBuilder {
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private Map<String, List> inParamMap = new HashMap<String, List>();

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public Map<String, List> getInParamMap() {
        return inParamMap;
    }

    public static SqlMapConditionBuilder builder() {
        return new SqlMapConditionBuilder();
    }

    private SqlMapConditionBuilder() {
    }

    public SqlMapConditionBuilder in(String paramName, List inList) {
        inParamMap.put(paramName, inList);
        return this;
    }

    public SqlMapConditionBuilder putAll(Map<String, Object> paramMap) {
        this.paramMap.putAll(paramMap);
        return this;
    }

    public SqlMapConditionBuilder put(String key, Object value) {
        this.paramMap.put(key, value);
        return this;
    }

}
