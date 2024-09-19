package com.techie.ecommerce.controller.api;

import com.techie.ecommerce.domain.dto.CartDto;
import com.techie.ecommerce.domain.dto.CartItemDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Tag(name = "Cart", description = "the Cart API")
public interface CartApi {


    @Operation(
            summary = "Create a new cart",
            description = "Creates a new cart with the provided cart details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid cart data provided")
    })
    ResponseEntity<CartDto> createCart(CartDto cartDto);


    @Operation(
            summary = "Add product to cart",
            description = "Adds a product to the cart identified by the provided cart ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully added to cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<CartDto> addToCart(Long cartId, ProductDto productDto);

    @Operation(
            summary = "Remove product from cart",
            description = "Removes a product from the cart identified by the provided cart ID and product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully removed from cart"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    ResponseEntity<ProductDto> removeFromCart(Long cartId, Integer productId);

    @Operation(
            summary = "Get cart item",
            description = "Fetches a product from the cart identified by the provided cart ID and product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully fetched from cart"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    ResponseEntity<ProductDto> getCartItem(Long cartId, Integer productId);

    @Operation(
            summary = "Get all cart items",
            description = "Fetches all products in the cart identified by the provided cart ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully fetched from cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
     ResponseEntity<List<CartItemDto>>getCartItems(Long cartId);

    @Operation(
            summary = "Remove all items from cart",
            description = "Removes all products from the cart identified by the provided cart ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All products successfully removed from cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    ResponseEntity<Void> removeAllCartItems(Long cartId);

    @Operation(
            summary = "Get cart total price",
            description = "Fetches the total price of all products in the cart identified by the provided cart ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total price successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    ResponseEntity<Double> getCartTotalPrice(Long cartId);

    @Operation(
            summary = "Check Inventory Before CheckOut",
            description = "Check the availability of   all products in the cart identified by the provided cart ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available products "),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    ResponseEntity<String> checkInventoryBeforeCheckOut(@PathVariable Long cartId);
}
