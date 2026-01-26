package com.works.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {

    @Bean
    public ModelMapper modelMapper() {
        System.out.println("ModelMapper Call");
        return new ModelMapper();
    }

}
