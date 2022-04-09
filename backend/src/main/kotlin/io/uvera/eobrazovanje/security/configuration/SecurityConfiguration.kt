package io.uvera.eobrazovanje.security.configuration

import io.uvera.eobrazovanje.security.filter.JwtAuthFilter
import io.uvera.eobrazovanje.security.service.CustomUserDetailsService
import io.uvera.eobrazovanje.util.extensions.configurationSource
import io.uvera.eobrazovanje.util.extensions.corsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val userDetailsService: CustomUserDetailsService,
    private val jwtAuthenticationFilter: JwtAuthFilter,
    private val jwtAuthEntryPoint: JwtAuthEntryPoint,
) : WebSecurityConfigurerAdapter() {

    @Bean(name = ["delegatingPasswordEncoder"])
    fun delegatingPasswordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(delegatingPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http {
            cors {
                configurationSource {
                    corsConfiguration {
                        allowedOrigins = listOf("*")
                        allowedHeaders = listOf("*")
                        allowedMethods = listOf(
                            HttpMethod.GET,
                            HttpMethod.HEAD,
                            HttpMethod.POST,
                            HttpMethod.DELETE,
                            HttpMethod.PUT,
                            HttpMethod.OPTIONS
                        ).map(HttpMethod::toString)
                    }
                }
            }
            csrf { disable() }
            httpBasic { disable() }
            logout { disable() }
            authorizeRequests {
                authorize("/api/auth/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            exceptionHandling {
                authenticationEntryPoint = jwtAuthEntryPoint
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter)
        }
    }
}
