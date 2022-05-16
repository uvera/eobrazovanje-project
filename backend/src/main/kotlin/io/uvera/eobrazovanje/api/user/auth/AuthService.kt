package io.uvera.eobrazovanje.api.user.auth

import io.uvera.eobrazovanje.api.user.auth.dto.AuthenticationRequestDTO
import io.uvera.eobrazovanje.api.user.auth.dto.TokenResponseDTO
import io.uvera.eobrazovanje.api.user.auth.dto.WhoAmIDTO
import io.uvera.eobrazovanje.common.repository.UserRepository
import io.uvera.eobrazovanje.error.exception.UserNotFoundException
import io.uvera.eobrazovanje.security.service.JwtAccessService
import io.uvera.eobrazovanje.security.service.JwtRefreshService
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(
    protected val jwtAccessService: JwtAccessService,
    protected val jwtRefreshService: JwtRefreshService,
    protected val userDetailsService: UserDetailsService,
    protected val userRepository: UserRepository,
    protected val authManager: AuthenticationManager,
) {
    fun whoAmI(): WhoAmIDTO {
        val principal by principalDelegate()
        val email = principal.email
        val user =
            userRepository.findByEmail(email)
                ?: throw UserNotFoundException("User by specified email [$email] not found")
        return WhoAmIDTO.fromUser(user)
    }

    fun authenticate(dto: AuthenticationRequestDTO) = authenticate(dto.email, dto.password)
    fun authenticate(email: String, password: String): TokenResponseDTO {
        // throws exception if failed
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                email, password
            )
        )
        return generateTokensByEmail(email)
    }

    fun generateTokensByEmail(email: String): TokenResponseDTO {
        // load userDetails from database
        val userDetails = userDetailsService.loadUserByUsername(email)
        // generate access token
        val accessToken: String = jwtAccessService.generateToken(userDetails)
        // generate longer lasting refresh token
        val refreshToken: String = jwtRefreshService.generateToken(userDetails)

        return TokenResponseDTO(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun generateTokensFromJwsRefreshToken(jws: String): TokenResponseDTO {
        // throw exception if token is invalid
        if (!jwtRefreshService.validateToken(jws))
            throw BadCredentialsException("Invalid refresh token")

        // fetch userDetails by subject parsed from refresh token
        val subject = jwtRefreshService.getClaimsFromToken(jws)?.subject
        val userDetails = userDetailsService.loadUserByUsername(subject)

        // throw if user's account is disabled otherwise return new token
        if (!userDetails.isEnabled) throw DisabledException("Account disabled")

        return TokenResponseDTO(
            accessToken = jwtAccessService.generateToken(userDetails),
            refreshToken = jwtRefreshService.generateToken(userDetails)
        )
    }

    fun getAllUsers(): Any = repo {
        return@repo findAllUsers()
    }
}
