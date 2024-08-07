package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.repository.CartRepository;
import com.techie.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return cartRepository.save(cartEntity);
    }

    @Override
    public CartEntity addToCart(Long cartId, ProductDto productDto) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if(cart != null){
            ProductEntity product = productRepository.findById(productDto.getProductId()).orElse(null);
            if(product != null){
                cart.getProductEntities().add(product);
                return cartRepository.save(cart);
            }
        }
        return null;
    }

    @Override
    public void delete(Long cartId, Long productId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if(cart != null){
            List<ProductEntity> productEntities = cart.getProductEntities();
            productEntities.removeIf(productEntity ->
                    productEntity.getProductId().equals(productId));
            cartRepository.save(cart);
        }
    }

    @Override
    public Optional<ProductEntity> getCartItem(Long cartId, Long productId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null){
            return cart.getProductEntities().stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<ProductEntity> getCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null){
            return cart.getProductEntities();
        }
            return List.of();
    }

    @Override
    public void removeAllCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null){
            cart.getProductEntities().clear();
            cartRepository.save(cart);
        }

    }

    @Override
    public double getCartTotalPrice(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null){
            return cart.getProductEntities().stream().mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
        }

            return 0.0;
    }
}
