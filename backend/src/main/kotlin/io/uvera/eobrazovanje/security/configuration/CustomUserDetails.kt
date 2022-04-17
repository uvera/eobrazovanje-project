package io.uvera.eobrazovanje.security.configuration

import io.uvera.eobrazovanje.common.repository.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(user: User) : UserDetails {
    val email: String = user.email
    private val password: String = user.password
    private val active = user.active
    private val authorities: MutableList<GrantedAuthority> = mutableListOf( SimpleGrantedAuthority("${RoleEnum.ROLE_PREFIX_VALUE}${user.role}"))

    override fun getUsername(): String = this.email

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = this.authorities

    override fun getPassword(): String = this.password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = active
}
