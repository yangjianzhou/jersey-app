package com.yangjianzhou.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * Created by yangjianzhou on 16-4-11.
 */
public abstract class AbstractJerseyDAO extends BaseDAO{
    @Autowired
    @Qualifier("sqlMapClientTemplate")
    private SqlMapClientTemplate sqlMapClientTemplate;

    @Override
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

}
