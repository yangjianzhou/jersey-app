package com.yangjianzhou.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
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

    public ReadFileThread(String fileName, TradeRecordService tradeRecordService) {
        this.fileName = fileName;
        this.tradeRecordService = tradeRecordService;
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
                List<String> contents = Splitter.on("-").trimResults().omitEmptyStrings().splitToList(lineContent);
                lineCount++;
                TradeRecordDTO tradeRecordDTO = buildTradeRecord(contents);
                tradeRecordDTOList.add(tradeRecordDTO);
                if (lineCount == 200) {
                    lineCount = 0;
                    tradeRecordService.batchInsert(tradeRecordDTOList);
                    tradeRecordDTOList.clear();
                }
            }
            if (tradeRecordDTOList != null) {
                tradeRecordService.batchInsert(tradeRecordDTOList);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Thread : " + Thread.currentThread().getName() + ", time spend : " + (endTime - startTime));
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error======================================");
        } finally {
        }
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
