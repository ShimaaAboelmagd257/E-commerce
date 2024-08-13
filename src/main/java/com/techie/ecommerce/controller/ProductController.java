package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.security.JwtTokenProvider;
import com.techie.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
  //  private final JwtTokenProvider jwtTokenProvider;
   // private final AuthenticationManager authenticationManager;
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
      //  this.jwtTokenProvider = jwtTokenProvider;
       // this.authenticationManager = authenticationManager;
    }
    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam String username){
        //String token = JwtTokenProvider.createToken(username);
        return service.fetchAllproducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return service.fetchProductById(id);
    }

}
