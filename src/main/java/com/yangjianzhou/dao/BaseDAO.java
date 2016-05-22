package com.yangjianzhou.dao;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjianzhou on 16-4-11.
 */
public abstract class BaseDAO extends QuerySqlExecutor{

    protected abstract SqlMapClientTemplate getSqlMapClientTemplate();

    @SuppressWarnings("unchecked")
    protected <T> T insert(String statement, Object param) {
        return (T) this.getSqlMapClientTemplate().insert(generate(statement), param);
    }

    @SuppressWarnings("unchecked")
    public <T> T insert(String statement) throws DataAccessException {
        return (T) this.getSqlMapClientTemplate().insert(generate(statement));
    }

    protected int update(String statement, Object param) {
        return this.getSqlMapClientTemplate().update(generate(statement), param);
    }

    protected void update(String statement, Object parameterObject, int requiredRowsAffected)
            throws DataAccessException {
        this.getSqlMapClientTemplate().update(generate(statement), parameterObject, requiredRowsAffected);
    }

    public int update(String statement) throws DataAccessException {
        return this.getSqlMapClientTemplate().update(generate(statement));
    }

    @SuppressWarnings("unchecked")
    protected <T> T queryForObject(String statement) {
        return (T) this.getSqlMapClientTemplate().queryForObject(generate(statement));
    }

    @SuppressWarnings("unchecked")
    protected <T> T queryForObject(String statement, Object param) {
        return (T) this.getSqlMapClientTemplate().queryForObject(generate(statement), param);
    }

    protected Map queryForMap(String statement, Object param, String keyProperty) {
        return this.getSqlMapClientTemplate().queryForMap(generate(statement), param, keyProperty);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> queryForList(String statement, Object param) {
        return this.getSqlMapClientTemplate().queryForList(generate(statement), param);
    }
    /**
     * 批量插入
     *
     * @throws DataAccessException
     */
    public Integer batchInsert(final String statementName, final Collection<? extends Object> parameterObjects)
            throws DataAccessException {
        return batchAction(generate(statementName), parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                    throws SQLException {
                executor.insert(statementName, parameterObject);
            }
        });
    }

    /**
     * 批量更新
     *
     * @return 成功执行的数量
     * @throws DataAccessException
     */
    public Integer batchUpdate(final String statementName, final Collection<? extends Object> parameterObjects)
            throws DataAccessException {
        return batchAction(generate(statementName), parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                    throws SQLException {
                executor.update(statementName, parameterObject);
            }
        });
    }
    /**
     * 可以批量指定的动作
     *
     * @return 成功执行的数量
     * @throws DataAccessException
     */
    private Integer batchAction(final String statementName, final Collection<? extends Object> parameterObjects,
                                final BatchAction batchAction) throws DataAccessException {
        Object ret = getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameterObjects == null || parameterObjects.isEmpty()) {
                    return 0;
                } else {
                    executor.startBatch();
                    for (Object parameterObject : parameterObjects) {
                        batchAction.doAction(executor, statementName, parameterObject);
                    }
                    return executor.executeBatch();
                }
            }
        });

        //
        if (ret instanceof Integer) {
            return (Integer) ret;
        } else {
            return 0;
        }
    }

    private String generate(String statement) {
        String namespace = nameSpace();
        if (StringUtils.isNotBlank(namespace)) {
            return nameSpace() + "." + statement;
        }
        return statement;
    }

    protected String nameSpace() {
        return null;
    }

}

/**
 * 可以批量执行的动作接口
 *
 * @author bob.panl
 */
interface BatchAction {

    public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
            throws SQLException;
}

