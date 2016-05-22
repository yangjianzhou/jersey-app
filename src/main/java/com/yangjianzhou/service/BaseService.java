package com.yangjianzhou.service;

import com.yangjianzhou.dao.ProductDAO;
import com.yangjianzhou.dao.ProductOneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangjianzhou on 16-4-16.
 */
@Service
public class BaseService {

    @Autowired
    protected ProductDAO productDAO ;

    @Autowired
    protected ProductOneDAO productOneDAO ;
}
