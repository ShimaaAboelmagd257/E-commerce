package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.CartItemEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.repository.CartRepository;
import com.techie.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public CartEntity save(CartEntity cartEntity) {
        if (cartEntity.getCartId() != null && cartRepository.existsById(cartEntity.getCartId())) {
            throw new IllegalArgumentException("This cart id '" + cartEntity.getCartId() + "' is already taken.");
        }
        return cartRepository.save(cartEntity);
    }
    @Override
    public Optional<CartEntity> findById(Long cartId){
        return cartRepository.findById(cartId);
    }
    @Override
    public CartEntity addToCart(Long cartId, ProductDto productDto) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));
            ProductEntity product = productRepository.findById(productDto.getId()).orElseThrow(()-> new  ResourceNotFoundException( "Product Not Found"));
                CartItemEntity existedCartItem =cart.getCartItems().stream()
                        .filter(item -> item.getProduct().getId().equals(product.getId()))
                        .findFirst()
                        .orElse(null);
                if (existedCartItem != null){
                    existedCartItem.setQuantity(existedCartItem.getQuantity()+1);
                }else {
                    CartItemEntity newCartItem = CartItemEntity.builder()
                            .product(product)
                            .cart(cart)
                            .quantity(1).
                            build();
                    cart.getCartItems().add(newCartItem);
                }
                return cartRepository.save(cart);
    }

    @Override
    public void delete(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            List<CartItemEntity> cartItemEntities = cart.getCartItems();
            cartItemEntities.removeIf(cartItem ->
                    cartItem.getProduct().getId().equals(productId));
            cartRepository.save(cart);

    }

    @Override
    public Optional<CartItemEntity> getCartItem(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));
            return cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

    }

    @Override
    public List<CartItemEntity> getCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            return cart.getCartItems();

    }

    @Override
    public void removeAllCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            cart.getCartItems().clear();
            cartRepository.save(cart);
    }

    @Override
    public double getCartTotalPrice(Long cartId) {

        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

        return cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    ProductEntity entity = cartItem.getProduct();
                    return entity.getPrice() * cartItem.getQuantity();
                })
                .sum();


    }
    @Override
    public boolean checkInventoryBeforeCheckOut(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException("Cart not found"));
         for (CartItemEntity item:cart.getCartItems()){
             ProductEntity entity = productRepository.findById(item.getProduct().getId()).orElseThrow(()-> new NotFoundException("Product not found"));
             if (entity.getQuantity() < item.getQuantity()){
                 return false;
             }
         }
       return true;

    }

}
