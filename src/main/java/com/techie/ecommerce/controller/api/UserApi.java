package com.techie.ecommerce.controller.api;

import com.techie.ecommerce.domain.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "User", description = "the User API")
public interface UserApi {

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    })
    ResponseEntity<?> createUser(UserDto userDto);

    @Operation(
            summary = "Get user by ID",
            description = "Fetches the user identified by the provided user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully fetched"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserDto> getUserById(Long userId);

    @Operation(
            summary = "Get all users",
            description = "Fetches all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully fetched")
    })
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(
            summary = "Update user",
            description = "Updates the user identified by the provided user ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    })
    ResponseEntity<UserDto> updateUser(Long userId, UserDto userDto);

    @Operation(
            summary = "Delete user",
            description = "Deletes the user identified by the provided user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Void> deleteUser(Long userId);
}
