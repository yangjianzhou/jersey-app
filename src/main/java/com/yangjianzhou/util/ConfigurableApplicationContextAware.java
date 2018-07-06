package com.yangjianzhou.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.unitils.spring.SpringModule;

@Component
public class ConfigurableApplicationContextAware implements InitializingBean {

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext ;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringModule.setApplicationContext(configurableApplicationContext);
    }
}
