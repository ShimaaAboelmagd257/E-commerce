package com.techie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class CartServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CartServiceApplication.class, args);

        System.out.println( "Hello World!" );
    }
}
