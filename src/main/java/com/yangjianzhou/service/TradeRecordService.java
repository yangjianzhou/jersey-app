package com.yangjianzhou.service;

import com.yangjianzhou.dao.ProductDAO;
import com.yangjianzhou.dao.TradeRecordDAO;
import com.yangjianzhou.dao.enums.ProductType;
import com.yangjianzhou.dto.ProductDTO;
import com.yangjianzhou.dto.TradeRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TradeRecordDAO tradeRecordDAO ;
    @Autowired
    private ProductDAO productDAO ;
    
    public void  parseFile(){
        ProductDTO productDTO = buildProductDTO();
        productDAO.insert(productDTO);
        productDTO.setName("FRUIT");

        List<TradeRecordDTO> tradeRecordDTOs = buildTradeRecordList();

        batchInsert(tradeRecordDTOs , productDTO);

    }

    @Transactional
    public void batchInsert(List<TradeRecordDTO> tradeRecordDTOList , ProductDTO productDTO){
        tradeRecordDAO.batchInsert(tradeRecordDTOList);
        productDAO.updateNameById(productDTO);
    }



    @Transactional
    public void batchInsert(List<TradeRecordDTO> tradeRecordDTOList){
        tradeRecordDAO.batchInsert(tradeRecordDTOList);
    }

    public ProductDTO buildProductDTO(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("book");
        productDTO.setType(ProductType.BOOK);
        productDTO.setVersion(1);
        productDTO.setCreatedAt(new Date());
        productDTO.setCreatedBy("SYS");
        productDTO.setUpdatedAt(new Date());
        productDTO.setUpdatedBy("SYS");
        return  productDTO ;
    }

    public List<TradeRecordDTO>  buildTradeRecordList(){
        List<TradeRecordDTO> tradeRecordDTOs = new ArrayList<>();

        for(int index = 0 ; index < 15100; index ++){
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

        for(int index = 100 ; index < 200; index ++){
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

        return tradeRecordDTOs ;
    }

}
