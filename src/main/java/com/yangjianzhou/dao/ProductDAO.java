package com.yangjianzhou.dao;

import com.yangjianzhou.dto.ProductDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjianzhou on 16-4-13.
 */

@Repository
public class ProductDAO extends AbstractJerseyDAO {

    public void insert(ProductDTO productDTO) {
        this.insert("tb_product.insert", productDTO);
    }

    public List<ProductDTO> selectAll() {
        return this.queryForList("tb_product.selectAll", null);
    }

    public int updateNameById(ProductDTO productDTO) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", productDTO.getId());
        params.put("name", productDTO.getName());
        params.put("version", productDTO.getVersion());
        params.put("updatedAt", productDTO.getUpdatedAt());
        params.put("updatedBy", productDTO.getUpdatedBy());
        return this.update("tb_product.updateNameById", params);
    }

    public List<ProductDTO> paginationSelect(int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", pageNo * pageSize);
        params.put("pageSize", pageSize);
        return this.queryForList("tb_product.paginationSelect", params);
    }

    public int count() {
        return this.queryForObject("tb_product.count", null);
    }

}
