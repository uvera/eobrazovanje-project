package io.uvera.eobrazovanje.api.user.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.user.auth.dto.AuthenticationRequestDTO
import io.uvera.eobrazovanje.api.user.auth.dto.RefreshRequestDTO
import io.uvera.eobrazovanje.api.user.auth.dto.TokenResponseDTO
import io.uvera.eobrazovanje.api.user.auth.dto.WhoAmIDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

//region SwaggerDoc
@Tag(description = "Endpoints for authenticating users.", name = "auth")
//endregion
@RestController
@RequestMapping("/api/auth")
class AuthController(
    protected val authService: AuthService,
) {

    //region SwaggerDoc
    @Operation(
        summary = "Authenticate",
        description = "Authenticated by email and password",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful authentication",
            content = [(Content(schema = Schema(implementation = TokenResponseDTO::class)))]
        ),
        ApiResponse(
            responseCode = "400", description = "Invalid DTO"
        ),
        ApiResponse(
            responseCode = "401",
            description = "Auth error",
        )
    )
    //endregion
    @PostMapping("/login")
    fun createAuthenticationToken(
        @Validated @RequestBody authenticationRequest: AuthenticationRequestDTO,
    ): AnyResponseEntity = authService.authenticate(authenticationRequest).ok

    //region SwaggerDoc
    @Operation(
        summary = "Refresh the tokens",
        description = "Refresh both access and the refresh token with older refresh token",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful refresh",
            content = [Content(schema = Schema(implementation = TokenResponseDTO::class))],
        ),
        ApiResponse(
            responseCode = "400", description = "Invalid DTO"
        ),
        ApiResponse(
            responseCode = "401",
            description = "Invalid token or account disabled",
        )
    )
    //endregion
    @PostMapping("/refresh")
    fun refreshToken(
        @Validated @RequestBody refreshRequest: RefreshRequestDTO,
    ): AnyResponseEntity = authService.generateTokensFromJwsRefreshToken(refreshRequest.token).ok

    //region SwaggerDoc
    @Operation(
        summary = "Get your own info",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "DTO Returned",
            content = [(Content(schema = Schema(implementation = WhoAmIDTO::class)))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "User not found",
        )
    )
    //endregion
    @PreAuthorize("authenticated")
    @GetMapping("/whoami")
    fun whoAmI(): ResponseEntity<WhoAmIDTO> =
        authService.whoAmI().ok
}
