package service;

import com.yangjianzhou.dto.ProductDTO;
import com.yangjianzhou.service.ProductService;
import org.junit.Assert;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public class ProductServiceTest extends BaseServiceTest {

    @SpringBeanByType
    private ProductService productService;

    @org.junit.Test
    @DataSet({"/data/tb_product.xls"})
    public void test_insert() {

        ProductDTO productDTO = productService.queryProductById(4L);
        Assert.assertTrue("zhangsan".equals(productDTO.getName()));
    }
}
