package com.yangjianzhou.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yangjianzhou.util.ListPartitionPolishingUtil;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangjianzhou on 16-4-11.
 */
public abstract class QuerySqlExecutor {

    protected abstract SqlMapClientTemplate getSqlMapClientTemplate();

    /**
     * 注意：
     * 1、查询出来的结果需要重新排序
     * 2、查询出来的结果可能重复，需要自行去重
     * <pre>
     * case 0 name='abc'
     * case 1 id in(1,2,3,4,5)
     * case 2 id in(1,2,3,4,5) and status in (1,2,3,4,5,6,7)
     *    ==>id in (1,2,3,4,5....5) and status status in (1,2,3,4,5,6,7....7)
     * case 3 id in(1,2,3,4,5...501) and status in (1,2,3,4,5,6,7)
     *    ==>id in (1,2,3...500) and status status in (1,2,3,4,5,6,7....7)
     *    ==>id in (501,501,501...501) and status status in (1,2,3,4,5,6,7....7)
     * case 4 id in(1,2,3,4,5) and status in () ==>返回空列表
     * </pre>
     *
     * @param <T>
     * @return
     */
    public <T> List<T> queryForListBySqlMapCondition(String sqlMapId, SqlMapConditionBuilder querySqlBuilder) {
        // case 1
        Map<String, Object> paramMap = querySqlBuilder.getParamMap();
        Map<String, List> inParamMap = querySqlBuilder.getInParamMap();

        if (inParamMap.size() == 0) {
            return getSqlMapClientTemplate().queryForList(sqlMapId, paramMap);
        }

        // case 2
        List<T> resultList = Lists.newLinkedList();

        // 分片并合并[{"key":"id",value:[1,2,3,4]},{"key":"id",value:[5,6,7,8]}],[{"key":"status",value:[1,2,3]},{"key":"status",value:[4,5,6]}]
        List<Set<ListPartitionPolishingUtil.KeyValueList>> paramsList = ListPartitionPolishingUtil.partitionAndCombine(inParamMap);

        // 笛卡尔积:
        // {id:[1,2,3,4],status:[1,2,3]},
        // {id:[1,2,3,4],status:[4,5,6]},
        // {id:[5,6,7,8],status:[1,2,3]},
        // {id:[5,6,7,8],status:[4,5,6]}
        Set<List<ListPartitionPolishingUtil.KeyValueList>> listSet = Sets.cartesianProduct(paramsList);
        for (List<ListPartitionPolishingUtil.KeyValueList> lists : listSet) {
            Map queryMap = new HashMap();
            queryMap.putAll(paramMap);
            for (ListPartitionPolishingUtil.KeyValueList keyValueList : lists) {
                queryMap.put(keyValueList.getKey(), keyValueList.getValue());
            }
            resultList.addAll(getSqlMapClientTemplate().queryForList(sqlMapId, queryMap));

        }

        return resultList;
    }


}
