package com.serpstat.restapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.serpstat.restapi")
public class AppConfiguration extends WebMvcConfigurerAdapter {
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
    		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5242880); // set the size limit
        return resolver;
    }
}
