package com.yangjianzhou.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangjianzhou on 17-7-22.
 */

@Service
public class AService {

    @Autowired
    private BService bService ;

    public void  test(){
        System.out.println("this is a test");
    }

}
