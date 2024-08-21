package com.techie.ecommerce.controller.api;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Category", description = "the Category API")
public interface CategoryApi {

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category with the provided category details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid category data provided")
    })
    ResponseEntity<?> createCategory(CategoryDto categoryDto);

    @Operation(
            summary = "Get products by category",
            description = "Fetches all products belonging to the category identified by the provided category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    ResponseEntity<Page<ProductDto>> getProductsByCategory(int page , int size,Integer id);

    @Operation(
            summary = "Get all categories",
            description = "Fetches all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully fetched")
    })
    ResponseEntity<Page<CategoryDto>> getAllCategories(int page , int size );

    @Operation(
            summary = "Get category by ID",
            description = "Fetches the category identified by the provided category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    CategoryDto getCategoryById(Integer id);

    @Operation(
            summary = "Update category",
            description = "Updates the category identified by the provided category ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully updated"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid category data provided")
    })
    ResponseEntity<?> updateCategory(Integer id, CategoryDto categoryDto);

    @Operation(
            summary = "Delete category",
            description = "Deletes the category identified by the provided category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    ResponseEntity<Void> deleteCategory(Integer id);
}
