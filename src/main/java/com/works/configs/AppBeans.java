package com.works.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

@Configuration
public class AppBeans {

    @Bean(name = "modelMapperDefault")
    public ModelMapper modelMapperx() {
        return new ModelMapper();
    }

    @Bean(name = "modelMapperConfig")
    public ModelMapper modelMappery(HttpServletRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IAction customerAction() {
        return new CustomerAction();
    }

    @Bean
    public IAction userAction() {
        return new UserAction();
    }

}
