package com.yangjianzhou.bean;

import com.yangjianzhou.dto.ProductDTO;

import java.util.List;

/**
 * Created by yangjianzhou on 16-4-24.
 */
public class ProductPaginationBean {

    private int totalCount;

    private List<ProductDTO> productDTOList;

    public ProductPaginationBean(int totalCount, List<ProductDTO> productDTOList) {
        this.totalCount = totalCount;
        this.productDTOList = productDTOList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }
}
