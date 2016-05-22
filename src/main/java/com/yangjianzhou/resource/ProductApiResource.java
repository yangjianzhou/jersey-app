package com.yangjianzhou.resource;

import com.google.common.collect.Lists;
import com.sun.jersey.api.core.InjectParam;
import com.yangjianzhou.bean.ProductPaginationBean;
import com.yangjianzhou.util.ReadFileThread;
import com.yangjianzhou.bean.ResultGson;
import com.yangjianzhou.dto.ProductDTO;
import com.yangjianzhou.service.ProductService;
import com.yangjianzhou.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by yangjianzhou on 16-4-17.
 */

@Path("test")
@Component
public class ProductApiResource {
    @InjectParam
    @Resource
    private ProductService productService;

    @Autowired
    private TradeRecordService tradeRecordService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @GET
    @Path("insert")
    @Produces(MediaType.APPLICATION_JSON)
    public String testInsert() {
        productService.saveProduct();
        return "PENDING";
    }

    @GET
    @Path("multi-batch-insert")
    @Produces(MediaType.APPLICATION_JSON)
    public String batchInsert(){
        List<String> fileNames = Lists.newArrayList("dailyIncomeFile.txt_0", "dailyIncomeFile.txt_1", "dailyIncomeFile.txt_2", "dailyIncomeFile.txt_3",
                "dailyIncomeFile.txt_4", "dailyIncomeFile.txt_5", "dailyIncomeFile.txt_6", "dailyIncomeFile.txt_7",
                "dailyIncomeFile.txt_8", "dailyIncomeFile.txt_9", "dailyIncomeFile.txt_10");
        String pathPrefix = "/home/yangjianzhou/Test";

        for (String fileName : fileNames) {
            taskExecutor.execute(new ReadFileThread(pathPrefix + "/" + fileName, tradeRecordService));
        }
        return "true";
    }

    @GET
    @Path("get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultGson<List<ProductDTO>> getAllProduct() {
        ResultGson<List<ProductDTO>> resultGson = productService.getAllProduct();
        return resultGson;
    }

    @GET
    @Path("update-product-name")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateProductName(@QueryParam("id") int id, @QueryParam("version") int version, @QueryParam("name") String name) {
        productService.updateProductName(id, version, name);
        return "PENDING";
    }

    @GET
    @Path("pagination-query")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultGson<ProductPaginationBean> paginationQuery(@QueryParam("pageSize") int pageSize, @QueryParam("pageNo") int pageNo) {
        ResultGson<ProductPaginationBean> paginationBeanResultGson = productService.mergePaginationQuery(pageNo, pageSize);
        return paginationBeanResultGson;
    }

    @GET
    @Path("batch-insert")
    @Produces(MediaType.APPLICATION_JSON)
    public String paginationQuery() {
        tradeRecordService.parseFile();
        return "success";
    }

}
