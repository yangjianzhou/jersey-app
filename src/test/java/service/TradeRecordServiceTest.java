package test.java.service;

import com.google.common.collect.Lists;
import com.yangjianzhou.util.ReadFileThread;
import com.yangjianzhou.service.TradeRecordService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * Created by yangjianzhou on 16-5-21.
 */
public class TradeRecordServiceTest extends BaseServiceTest {

    @Autowired
    private TradeRecordService tradeRecordService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void test_batchInsert() {
        List<String> fileNames = Lists.newArrayList("dailyIncomeFile.txt_0", "dailyIncomeFile.txt_1", "dailyIncomeFile.txt_2", "dailyIncomeFile.txt_3",
                "dailyIncomeFile.txt_4", "dailyIncomeFile.txt_5", "dailyIncomeFile.txt_6", "dailyIncomeFile.txt_7",
                "dailyIncomeFile.txt_8", "dailyIncomeFile.txt_9", "dailyIncomeFile.txt_10");
        String pathPrefix = "/home/yangjianzhou/Test";

        for (String fileName : fileNames) {
            taskExecutor.execute(new ReadFileThread(pathPrefix + "/" + fileName, tradeRecordService));
        }
    }

}
