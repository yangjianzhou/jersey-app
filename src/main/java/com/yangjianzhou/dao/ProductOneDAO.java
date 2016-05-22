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
public class ProductOneDAO extends AbstractJerseyDAO {

    public List<ProductDTO> paginationSelect(int pageNo , int pageSize){
        Map<String , Object> params = new HashMap<String , Object>();
        params.put("offset" , pageNo * pageSize);
        params.put("pageSize" , pageSize);
        return this.queryForList("tb_product_one.paginationSelect" , params) ;
    }

    public int count(){
        return this.queryForObject("tb_product_one.count" , null) ;
    }

}
