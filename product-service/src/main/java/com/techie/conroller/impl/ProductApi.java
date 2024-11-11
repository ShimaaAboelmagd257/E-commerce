package com.techie.conroller.impl;

import com.techie.domain.PageResponse;
import com.techie.domain.ProductCreation;
import com.techie.domain.ProductDto;
import com.techie.domain.ProductFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "Product", description = "the Product API")
public interface ProductApi {

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided")
    })
    ResponseEntity<?> createProduct(ProductCreation creation);

    @Operation(
            summary = "Get all products",
            description = "Fetches all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully fetched")
    })
    PageResponse<ProductDto> getAllProducts(int page, int size);



    @Operation(
            summary = "Get product by ID",
            description = "Fetches the product identified by the provided product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ProductDto getProductById(Integer id);

    @Operation(
            summary = "Update product",
            description = "Updates the product identified by the provided product ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided")
    })
    ResponseEntity<?> updateProduct(Integer id, ProductCreation productDto);

    @Operation(
            summary = "Delete product",
            description = "Deletes the product identified by the provided product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ResponseEntity<Void> deleteProduct(Integer id);

    @Operation(
            summary = "Filter products",
            description = "Fetches products based on the provided filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully filtered")
    })
    ResponseEntity<Page<ProductDto>> filterProducts(ProductFilter productFilter, int page, int size);
}
