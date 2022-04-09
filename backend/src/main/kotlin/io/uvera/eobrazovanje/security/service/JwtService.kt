package io.uvera.eobrazovanje.security.service

import io.jsonwebtoken.*
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

interface JwtService {
    fun generateToken(userDetails: UserDetails): String
    fun validateToken(token: String): Boolean
    fun getClaimsFromToken(token: String): Claims?
}

/**
 * Generic implementation for JWT handling
 */
@Service
class GenericTokenService {

    val logger by loggerDelegate()
    /**
     * Generates token from [userDetails] using [secret] [SecretKey]
     *
     * @param userDetails instance of [UserDetails] implementation
     * @param expirationInMinutes expiration in minutes as [Int]
     * @param secret used for signing the JWT
     *
     * @return generated token [String]
     */
    fun generateToken(userDetails: UserDetails, expirationInMinutes: Int, secret: SecretKey): String {
        val subject = userDetails.username
        val claims = mutableMapOf<String, Any>()
        claims["roles"] = userDetails.authorities.map { it.toString() }
        val issuedAt = Date(System.currentTimeMillis())
        val expiration = Calendar
            .getInstance()
            .also { calendar ->
                calendar.add(Calendar.MINUTE, expirationInMinutes)
            }
            .toInstant()
            .toEpochMilli()
            .let { millis ->
                Date(millis)
            }

        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(secret)
            .compact()
    }

    /**
     * Validates [token] against [secret]
     * @return [Boolean] indicating if validation passed
     */
    fun validateToken(token: String, secret: SecretKey): Boolean = try {
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
        true
    } catch (ex: SecurityException) {
        false
    } catch (ex: MalformedJwtException) {
        false
    } catch (ex: UnsupportedJwtException) {
        false
    } catch (ex: IllegalArgumentException) {
        false
    } catch (ex: ExpiredJwtException) {
        false
    }

    /**
     * Parses claims from tokens
     * @return nullable instance of [Claims]
     */
    fun getClaimsFromToken(token: String, secret: SecretKey): Claims? = try {
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    } catch (ex: Exception) {
        null
    }
}
