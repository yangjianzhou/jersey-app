package com.yangjianzhou.service;

import com.yangjianzhou.bean.ProductPaginationBean;
import com.yangjianzhou.bean.ResultGson;
import com.yangjianzhou.dao.enums.ProductType;
import com.yangjianzhou.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangjianzhou on 16-4-16.
 */

@Service
public class ProductService extends BaseService {

    public void saveProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("thisi si ");
        productDTO.setType(ProductType.CLOTH);
        productDTO.setCreatedAt(new Date());
        productDTO.setCreatedBy("SYS");
        productDTO.setUpdatedAt(new Date());
        productDTO.setUpdatedBy("SYS");
        productDAO.insert(productDTO);
    }

    public ResultGson<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productDTOs = productDAO.selectAll();
        return new ResultGson<>("000", "success", productDTOs);
    }

    public void updateProductName(int productId, int version, String name) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setUpdatedAt(new Date());
        productDTO.setUpdatedBy("SYS");
        productDTO.setName(name);
        productDTO.setId(productId);
        productDTO.setVersion(version);
        productDAO.updateNameById(productDTO);
    }

    public ResultGson<ProductPaginationBean> paginationOneQuery(int pageNo, int pageSize) {
        ResultGson<ProductPaginationBean> productPaginationResultGson = new ResultGson<ProductPaginationBean>("000", "success", null);
        int totalCount = productDAO.count();
        List<ProductDTO> productDTOList = productDAO.paginationSelect(pageNo, pageSize);
        ProductPaginationBean productPaginationBean = new ProductPaginationBean(totalCount, productDTOList);
        productPaginationResultGson.setData(productPaginationBean);
        return productPaginationResultGson;
    }

    public ResultGson<ProductPaginationBean> paginationSecondQuery(int pageNo, int pageSize) {
        ResultGson<ProductPaginationBean> productPaginationResultGson = new ResultGson<ProductPaginationBean>("000", "success", null);
        int totalCount = productOneDAO.count();
        List<ProductDTO> productDTOList = productOneDAO.paginationSelect(pageNo, pageSize);
        ProductPaginationBean productPaginationBean = new ProductPaginationBean(totalCount, productDTOList);
        productPaginationResultGson.setData(productPaginationBean);
        return productPaginationResultGson;
    }

    public ResultGson<ProductPaginationBean> mergePaginationQuery(int pageNo, int pageSize) {
        ResultGson<ProductPaginationBean> productPaginationResultGson = new ResultGson<ProductPaginationBean>("000", "success", null);
        ResultGson<ProductPaginationBean> paginationOneResultGson = paginationOneQuery(pageNo, pageSize);
        ResultGson<ProductPaginationBean> paginationTwoBeanResultGson = paginationSecondQuery(pageNo, pageSize);
        int firstTotalCount = paginationOneResultGson.getData().getTotalCount();
        int secondTotalCount = paginationTwoBeanResultGson.getData().getTotalCount();
        int totalCount = firstTotalCount + secondTotalCount;

        int firstTotalPage = firstTotalCount / pageSize;
        int firstMode = firstTotalCount % pageSize;

        if (firstTotalPage > pageNo) {
            ProductPaginationBean productPaginationBean = new ProductPaginationBean(totalCount, paginationOneResultGson.getData().getProductDTOList());
            productPaginationResultGson.setData(productPaginationBean);
            return productPaginationResultGson;
        }

        if (firstTotalPage < pageNo) {
            ResultGson<ProductPaginationBean> productPaginationFirstResultGson = paginationSecondQuery(pageNo - firstTotalPage - 1, pageSize);
            ResultGson<ProductPaginationBean> productPaginationSecondResultGson = paginationSecondQuery(pageNo - firstTotalPage, pageSize);
            List<ProductDTO> mergedProductList = mergeDataAfterRemoveDataOfFirstList(pageSize - firstMode, productPaginationFirstResultGson.getData().getProductDTOList(), productPaginationSecondResultGson.getData().getProductDTOList());
            ProductPaginationBean productPaginationBean = new ProductPaginationBean(totalCount, mergedProductList);
            productPaginationResultGson.setData(productPaginationBean);
            return productPaginationResultGson;
        }

        if (firstTotalPage == pageNo) {
            paginationOneResultGson = paginationOneQuery(pageNo, pageSize);
            paginationTwoBeanResultGson = paginationSecondQuery(0, pageSize);
            List<ProductDTO> productDTOList = mergeDataDeleteSomeIfPossible(paginationOneResultGson.getData().getProductDTOList(), paginationTwoBeanResultGson.getData().getProductDTOList(), pageSize);
            ProductPaginationBean productPaginationBean = new ProductPaginationBean(totalCount, productDTOList);
            productPaginationResultGson.setData(productPaginationBean);
            return productPaginationResultGson;
        }


        return productPaginationResultGson;
    }

    private List<ProductDTO> mergeDataAfterRemoveDataOfFirstList(int removeSize, List<ProductDTO> firstProductList, List<ProductDTO> secondProductList) {
        List<ProductDTO> resultProductList = new ArrayList<>();
        if (firstProductList.size() > removeSize) {
            resultProductList.addAll(firstProductList.subList(removeSize, firstProductList.size()));
        }

        int cutSize = removeSize > secondProductList.size() ? secondProductList.size() : removeSize;

        resultProductList.addAll(secondProductList.subList(0, cutSize));
        return resultProductList;
    }

    private List<ProductDTO> mergeDataDeleteSomeIfPossible(List<ProductDTO> firstProductList, List<ProductDTO> secondProductList, int pageSize) {
        List<ProductDTO> resultProductList = new ArrayList<>();
        resultProductList.addAll(firstProductList);
        resultProductList.addAll(secondProductList);

        if (resultProductList.size() > pageSize) {
            resultProductList = resultProductList.subList(0, pageSize);
        }

        return resultProductList;
    }

}
