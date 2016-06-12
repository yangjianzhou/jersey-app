package com.yangjianzhou.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yangjianzhou.dto.TradeRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by yangjianzhou on 16-4-13.
 */

@Repository
public class TradeRecordDAO extends AbstractJerseyDAO {

    @Autowired
    private SqlMapClient sqlMapClient;

    public void batchInsertWithSpring(final List<TradeRecordDTO> tradeRecordDTOList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (TradeRecordDTO tradeRecordDTO : tradeRecordDTOList) {
                    sqlMapClient.insert("tb_trade_record.insert", tradeRecordDTO);
                }
                executor.executeBatch();
                return null;
            }
        });
    }

    public void batchInsertWithIbatis(List<TradeRecordDTO> tradeRecordDTOList) {
        try {
            sqlMapClient.startBatch();
            for (TradeRecordDTO tradeRecordDTO : tradeRecordDTOList) {
                sqlMapClient.insert("tb_trade_record.insert", tradeRecordDTO);
            }
            sqlMapClient.executeBatch();
        } catch (Exception exp) {

        }

    }

    public void batchInsertWithJDBC(List<TradeRecordDTO> tradeRecordDTOList) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_jersey?useUnicode=true&characterEncode=utf-8", "jersey", "123456");
            Statement stmt = conn.createStatement();

            for (int index = 0; index < tradeRecordDTOList.size(); index++) {
                TradeRecordDTO tradeRecordDTO = tradeRecordDTOList.get(index);
                stmt.addBatch(" insert into tb_trade_record(created_at , created_by , updated_at , updated_by , user_id , product_id , amount , version) " +
                        "values(now() , 'SYS' , now() , 'SYS' ," + tradeRecordDTO.getUserId() + "," + tradeRecordDTO.getProductId() + "," + tradeRecordDTO.getAmount() + ",1)");
            }
            stmt.executeBatch();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

}
