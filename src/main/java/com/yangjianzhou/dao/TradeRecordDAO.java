package com.yangjianzhou.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yangjianzhou.dto.TradeRecordDTO;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yangjianzhou on 16-4-13.
 */

@Repository
public class TradeRecordDAO extends AbstractJerseyDAO {

    public void batchInsert(final List<TradeRecordDTO> tradeRecordDTOList) {
        final SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (TradeRecordDTO tradeRecordDTO : tradeRecordDTOList) {
                    //sqlMapClient.insert("tb_trade_record.insert", tradeRecordDTO);
                    executor.insert("tb_trade_record.insert", tradeRecordDTO);

                }
                executor.executeBatch();
                return null;
            }
        });
    }
}
