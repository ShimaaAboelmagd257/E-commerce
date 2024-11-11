package com.techie.ecommerce.config;

import com.techie.ecommerce.domain.dto.shippo.ShipmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.HashMap;

@Configuration
public class AppConfig {

//    @Bean
//    public ModelMapper modelMapper(){
//        return new ModelMapper();
//    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();



        return modelMapper;
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }
}
