package com.erick.spring.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.erick.spring")
public class SpringbootConfiguration implements WebMvcConfigurer {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
