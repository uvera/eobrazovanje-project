package io.uvera.eobrazovanje.security.service

import io.uvera.eobrazovanje.common.repository.UserRepository
import io.uvera.eobrazovanje.security.configuration.CustomUserDetails
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Primary
@Service
class CustomUserDetailsService(
    protected val repo: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { u -> repo.findByEmail(u) }
            ?: throw UsernameNotFoundException("User [username: $username] not found")
        return CustomUserDetails(user)
    }
}
