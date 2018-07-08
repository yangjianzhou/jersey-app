package com.yangjianzhou.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.unitils.spring.util.ApplicationContextFactory;

import java.util.List;

/**
 * point :
 * 1.need update
 * "SpringBootModule.applicationContextFactory.implClassName" -> "org.unitils.spring.util.ClassPathXmlApplicationContextFactory"
 *
 * AnnotationConfigWebApplicationContext
 */
public class SpringBootApplicationContextFactory implements ApplicationContextFactory {

    private static ConfigurableApplicationContext configurableApplicationContext ;

    public ConfigurableApplicationContext createApplicationContext(List<String> locations) {
        return configurableApplicationContext;
    }

    public static void setConfigurableApplicationContext(ConfigurableApplicationContext configurableApplicationContext) {
        SpringBootApplicationContextFactory.configurableApplicationContext = configurableApplicationContext;
    }
}
