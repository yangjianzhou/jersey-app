package com.yangjianzhou.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.unitils.spring.util.ApplicationContextFactory;

import java.util.List;

/**
 * point :
 * 1.need update
 * "SpringModule.applicationContextFactory.implClassName" -> "org.unitils.spring.util.ClassPathXmlApplicationContextFactory"
 *
 * AnnotationConfigWebApplicationContext
 */
@Component
public class SpringBootApplicationContextFactory implements ApplicationContextFactory {

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext ;

    @Override
    public ConfigurableApplicationContext createApplicationContext(List<String> locations) {
        return configurableApplicationContext;
    }
}
