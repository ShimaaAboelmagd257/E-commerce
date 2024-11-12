package com.techie.conroller.api;

import com.techie.domain.UserDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


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

    @Operation(
            summary = "Get user by username or email",
            description = "Retrieves a user's details using either the username or email address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserDto> getUserByUsernameOrEmail(
            @RequestParam String username,
            @RequestParam String email
    );


    @Operation(
            summary = "Change password",
            description = "Changes the password for the user identified by the provided user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password successfully changed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid password format")
    })
    ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody String newPassword
    );
   /* @Operation(
            summary = "Forgot password",
            description = "Sends a password reset token to the email address provided."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset token sent to email"),
            @ApiResponse(responseCode = "404", description = "User with the provided email not found")
    })
    ResponseEntity<Void> forgetPassword(
            Map<String, String> request
    );*/
    @Operation(
            summary = "Reset password",
            description = "Resets the user's password using the provided token and new password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password successfully reset"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Void> resetPassword(
            @RequestParam String token,
            @RequestBody Map<String, String> request
    );
    @Operation(
            summary = "Get user profile",
            description = "Retrieves the profile of the currently authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized or invalid token")
    })
    ResponseEntity<UserDto> getUserProfile();

}
