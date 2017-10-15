package service;

import com.yangjianzhou.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public class ProductServiceTest extends BaseServiceTest{

    @Autowired
    private ProductService productService ;

    @Test
    public void test_insert(){

        productService.saveProduct();
        Assert.assertTrue(true);
    }
}
