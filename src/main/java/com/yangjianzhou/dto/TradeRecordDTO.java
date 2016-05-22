package com.yangjianzhou.dto;

import java.math.BigDecimal;

/**
 * Created by yangjianzhou on 16-4-13.
 */
public class TradeRecordDTO extends BaseDTO{

    /**
     * 用户
     */
    private int userId ;

    /**
     * 产品
     */
    private int productId ;

    /**
     * 订单金额
     */
    private BigDecimal amount ;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
