package com.yangjianzhou.service;

import com.yangjianzhou.dao.ProductDAO;
import com.yangjianzhou.dao.TradeRecordDAO;
import com.yangjianzhou.dao.enums.ProductType;
import com.yangjianzhou.dto.ProductDTO;
import com.yangjianzhou.dto.TradeRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangjianzhou on 16-4-30.
 */
@Service
public class TradeRecordService extends BaseService {

    @Autowired
    private TradeRecordDAO tradeRecordDAO;
    @Autowired
    private ProductDAO productDAO;

    private static final String FILE_NAME = "/home/yangjianzhou/Test/dailyIncomeFile.txt";

    private static final int TOTAL_LINE_OF_BIG_FILE = 10000000;

    private static final int MAX_LINE_PER_SUB_FILE = 1000000;

    public void parseFile() {
        ProductDTO productDTO = buildProductDTO();
        productDAO.insert(productDTO);
        productDTO.setName("FRUIT");

        List<TradeRecordDTO> tradeRecordDTOs = buildTradeRecordList();

        batchInsert(tradeRecordDTOs, productDTO);

    }

    @Transactional
    public void batchInsert(List<TradeRecordDTO> tradeRecordDTOList, ProductDTO productDTO) {
        tradeRecordDAO.batchInsertWithSpring(tradeRecordDTOList);
        productDAO.updateNameById(productDTO);
    }


    //@Transactional
    public void batchInsertWithSpring(List<TradeRecordDTO> tradeRecordDTOList) {
        tradeRecordDAO.batchInsertWithSpring(tradeRecordDTOList);
    }

    //@Transactional
    public void batchInsertWithIbatis(List<TradeRecordDTO> tradeRecordDTOList) {
        tradeRecordDAO.batchInsertWithIbatis(tradeRecordDTOList);
    }

    public void batchInsertWithJDBC(List<TradeRecordDTO> tradeRecordDTOList) {
        tradeRecordDAO.batchInsertWithJDBC(tradeRecordDTOList);
    }

    public ProductDTO buildProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("book");
        productDTO.setType(ProductType.BOOK);
        productDTO.setVersion(1);
        productDTO.setCreatedAt(new Date());
        productDTO.setCreatedBy("SYS");
        productDTO.setUpdatedAt(new Date());
        productDTO.setUpdatedBy("SYS");
        return productDTO;
    }

    public List<TradeRecordDTO> buildTradeRecordList() {
        List<TradeRecordDTO> tradeRecordDTOs = new ArrayList<>();

        for (int index = 0; index < 151; index++) {
            TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
            tradeRecordDTO.setAmount(new BigDecimal(index));
            tradeRecordDTO.setProductId(index);
            tradeRecordDTO.setUserId(index);
            tradeRecordDTO.setCreatedAt(new Date());
            tradeRecordDTO.setCreatedBy("SYS");
            tradeRecordDTO.setUpdatedAt(new Date());
            tradeRecordDTO.setUpdatedBy("SYS");
            tradeRecordDTO.setVersion(1);
            tradeRecordDTOs.add(tradeRecordDTO);
        }

        TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
        tradeRecordDTO.setAmount(new BigDecimal(1));
        tradeRecordDTO.setProductId(10);
        tradeRecordDTO.setUserId(10);
        tradeRecordDTO.setCreatedAt(new Date());
        tradeRecordDTO.setCreatedBy("SYS");
        tradeRecordDTO.setUpdatedAt(new Date());
        tradeRecordDTO.setUpdatedBy("SYS");
        tradeRecordDTO.setVersion(10);
        tradeRecordDTOs.add(tradeRecordDTO);

        for (int index = 100; index < 200; index++) {
            TradeRecordDTO tradeRecord = new TradeRecordDTO();
            tradeRecord.setAmount(new BigDecimal(index));
            tradeRecord.setProductId(index);
            tradeRecord.setUserId(index);
            tradeRecord.setCreatedAt(new Date());
            tradeRecord.setCreatedBy("SYS");
            tradeRecord.setUpdatedAt(new Date());
            tradeRecord.setUpdatedBy("SYS");
            tradeRecord.setVersion(1);
            tradeRecordDTOs.add(tradeRecord);
        }

        return tradeRecordDTOs;
    }

    public void generateFile() {
        try {
            buildBigFile();
            splitBigFile();
        } catch (Exception exp) {

        }
    }

    public static void buildBigFile() throws Exception {
        FileWriter fileWritter = null;
        try {

            fileWritter = new FileWriter(FILE_NAME, false);
            long startTime = System.currentTimeMillis();
            for (int lineNum = 1; lineNum <= TOTAL_LINE_OF_BIG_FILE; lineNum++) {
                fileWritter.write(buildLineContent(lineNum));
            }
            long endTime = System.currentTimeMillis();
            System.out.println("build big file time spend : " + (endTime - startTime));

        } catch (Exception exp) {

        } finally {
            if (fileWritter != null) {
                fileWritter.close();
            }
        }
    }

    public static void splitBigFile() throws Exception {
        int fileCounter = 0;
        int lineCounter = 0;
        File bigFile = new File(FILE_NAME);
        FileReader fileReader = new FileReader(bigFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter fileWritter = new FileWriter(bigFile.getAbsolutePath() + "_" + fileCounter, false);
        String lineContent;
        long startTime = System.currentTimeMillis();
        try {
            while ((lineContent = bufferedReader.readLine()) != null) {
                lineCounter++;
                fileWritter.write(lineContent);
                fileWritter.write("\n");
                if (lineCounter % MAX_LINE_PER_SUB_FILE == 0) {
                    fileCounter++;
                    fileWritter.flush();
                    fileWritter.close();
                    fileWritter = new FileWriter(bigFile.getAbsolutePath() + "_" + fileCounter, false);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println(exp.getCause());
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (fileWritter != null) {
                fileWritter.close();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("split big file time spend : " + (endTime - startTime));
    }

    public static String buildLineContent(int lineNum) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lineNum);
        stringBuilder.append('-');
        stringBuilder.append(lineNum + 4);
        stringBuilder.append('-');
        stringBuilder.append(lineNum + 0.2);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
