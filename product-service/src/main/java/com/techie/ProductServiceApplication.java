package com.techie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProductServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductServiceApplication.class, args);

        System.out.println( "Hello World!" );
    }
}
