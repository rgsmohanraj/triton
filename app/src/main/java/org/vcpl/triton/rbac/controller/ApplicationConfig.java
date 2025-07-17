package org.vcpl.triton.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.vcpl.triton.rbac.service.GroupService;

@Configuration
public class ApplicationConfig {

    @Autowired
    private GroupService groupService;

    @Value("${thymeleaf.templates.location}")
    private String templateLocation;

    @Value("${thymeleaf.templates.extension}")
    private String templateExtension;

    @Bean
    CommandLineRunner loadKeycloakGroups(){
        return args -> {
            groupService.saveGroupsFromKeycloak();
        };
    }

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(templateLocation);
        return bean;
    }
}
