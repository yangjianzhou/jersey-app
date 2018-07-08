package com.yangjianzhou.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.unitils.spring.SpringBootModule;

@Configuration
@ConditionalOnClass(BootUnitilsJUnit4ClassRunner.class)
public class ConfigurableApplicationContextAware implements InitializingBean {

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext ;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringBootModule.setApplicationContext(configurableApplicationContext);
    }
}
