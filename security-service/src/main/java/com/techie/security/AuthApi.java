package com.techie.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "the Authentication Api")
public interface AuthApi {

    @Operation(
            summary = "Authenticate user",
            description = "Validate user request to login with Jwt ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
     ResponseEntity<?> authenticateUser( LoginRequest loginRequest);
}
