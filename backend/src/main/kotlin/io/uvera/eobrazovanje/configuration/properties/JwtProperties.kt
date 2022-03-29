package io.uvera.eobrazovanje.configuration.properties

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.validation.constraints.NotBlank


@ConfigurationProperties(prefix = "app.jwt.token.access")
@ConstructorBinding
class JwtAccessTokenProperties(
    @field:NotBlank val secret: SecretKey,
    @field:NotBlank val expirationInMinutes: Int,
)

@ConfigurationProperties(prefix = "app.jwt.token.refresh")
@ConstructorBinding
class JwtRefreshTokenProperties(
    @field:NotBlank val secret: SecretKey,
    @field:NotBlank val expirationInMinutes: Int,
)

@Component
@ConfigurationPropertiesBinding
class Base64SecretConverter : Converter<String, SecretKey> {
    override fun convert(source: String): SecretKey {
        val parsed = Base64.getEncoder().encodeToString(source.encodeToByteArray())
        return Keys.hmacShaKeyFor(parsed.toByteArray())
    }

}
