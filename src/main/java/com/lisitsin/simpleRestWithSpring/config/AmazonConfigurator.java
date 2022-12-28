package com.lisitsin.simpleRestWithSpring.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmazonConfigurator {

    @Bean
    public AmazonS3 createConfigurator(){
        return AmazonS3ClientBuilder.standard().build();
    }
}
