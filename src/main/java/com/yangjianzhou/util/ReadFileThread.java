package com.yangjianzhou.util;

import com.google.common.base.Splitter;
import com.yangjianzhou.bean.BatchType;
import com.yangjianzhou.dto.TradeRecordDTO;
import com.yangjianzhou.service.TradeRecordService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjianzhou on 16-5-21.
 */
public class ReadFileThread implements Runnable {

    private String fileName;

    private TradeRecordService tradeRecordService;

    private BatchType batchType;

    public ReadFileThread(String fileName, TradeRecordService tradeRecordService, BatchType batchType) {
        this.fileName = fileName;
        this.tradeRecordService = tradeRecordService;
        this.batchType = batchType;
    }

    @Override
    public void run() {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineContent = null;
            int lineCount = 0;
            List<TradeRecordDTO> tradeRecordDTOList = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            while ((lineContent = bufferedReader.readLine()) != null) {
                //List<String> contents = Lists.newArrayList(lineContent.split("-"));
                List<String> contents = Splitter.on('-').trimResults().omitEmptyStrings().splitToList(lineContent);
                lineCount++;
                TradeRecordDTO tradeRecordDTO = buildTradeRecord(contents);
                tradeRecordDTOList.add(tradeRecordDTO);
                if (lineCount == 200) {
                    lineCount = 0;
                    batchInsert(tradeRecordDTOList, batchType);
                    tradeRecordDTOList.clear();
                }
            }
            if (tradeRecordDTOList != null) {
                batchInsert(tradeRecordDTOList, batchType);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Thread : " + Thread.currentThread().getName() + ", time spend : " + (endTime - startTime));
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error======================================");
        } finally {
        }
    }

    private void batchInsert(List<TradeRecordDTO> tradeRecordDTOs, BatchType batchType) {
        if (batchType == BatchType.IBATIS) {
            tradeRecordService.batchInsertWithIbatis(tradeRecordDTOs);
            return;
        }else if(batchType ==BatchType.SPRING){
            tradeRecordService.batchInsertWithSpring(tradeRecordDTOs);
            return;
        }
        tradeRecordService.batchInsertWithJDBC(tradeRecordDTOs);

    }

    public TradeRecordDTO buildTradeRecord(List<String> contents) {
        TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
        tradeRecordDTO.setCreatedBy("SYS");
        tradeRecordDTO.setUpdatedBy("SYS");
        tradeRecordDTO.setUserId(Integer.valueOf(contents.get(0)));
        tradeRecordDTO.setProductId(Integer.valueOf(contents.get(1)));
        tradeRecordDTO.setAmount(new BigDecimal(contents.get(2)));
        return tradeRecordDTO;
    }
}
