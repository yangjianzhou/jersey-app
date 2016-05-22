package com.yangjianzhou.dto;

import com.yangjianzhou.dao.enums.ProductType;

/**
 * Created by yangjianzhou on 16-4-13.
 */
public class ProductDTO extends BaseDTO{

    /**
     * 产品名字
     */
    private String name ;

    /**
     * 产品类型
     */
    private ProductType type ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
