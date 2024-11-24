package com.techie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@ComponentScan(basePackages = {"com.techie.controller", "com.techie.service", "com.techie.repository"})
@EnableJpaRepositories(basePackages = "com.techie.repository")
@EntityScan(basePackages = "com.techie.domain")
@EnableAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProductServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductServiceApplication.class, args);

        System.out.println( "Hello World!" );
    }
}
