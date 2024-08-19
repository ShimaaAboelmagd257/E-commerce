package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.controller.api.CartApi;
import com.techie.ecommerce.domain.dto.CartDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartController implements CartApi {

    @Autowired
    private CartService cartService;
    @Autowired
    private Mapper<CartEntity , CartDto> cartMapper;
    @Autowired
    private Mapper<ProductEntity , ProductDto> productMapper;

    @Override
    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto){
        CartEntity cartEntity =cartMapper.mapFrom(cartDto);
        CartEntity savedCart = cartService.save(cartEntity);
        CartDto dto = cartMapper.mapTo(savedCart);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @Override
    @PostMapping(path = "/{cartId}/products")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long cartId, @RequestBody ProductDto productDto){

        CartEntity updatedCart = cartService.addToCart(cartId, productDto);
        CartDto cartDto = cartMapper.mapTo(updatedCart);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);

    }

    @Override
    @DeleteMapping(path = "/{cartId}/products/{productId}")
    public ResponseEntity<ProductDto> removeFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartService.delete(cartId,productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping(path = "/{cartId}/products/{productId}")
    public ResponseEntity<ProductDto> getCartItem (@PathVariable Long cartId , @PathVariable Long productId){
        Optional<ProductEntity> productEntity = cartService.getCartItem(cartId , productId);
        return productEntity.map( product -> {
            ProductDto productDto = productMapper.mapTo(product);
            return new ResponseEntity<>(productDto,HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Override
    @GetMapping(path = "/{cartId}/products")
    public ResponseEntity<List<ProductDto>> getCartItems(@PathVariable Long cartId){
        List<ProductEntity> productEntities = cartService.getCartItems(cartId);
        List<ProductDto>  productDtos = productEntities
                .stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }
    @Override
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> removeAllCartItems(@PathVariable Long cartId) {
        cartService.removeAllCartItems(cartId);
        return ResponseEntity.noContent().build();
    }
    @Override
    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getCartTotalPrice(@PathVariable Long cartId) {
        double total = cartService.getCartTotalPrice(cartId);
        return ResponseEntity.ok(total);
    }

}
