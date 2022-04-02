package io.uvera.eobrazovanje.api.user.auth.dto

import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.security.configuration.RoleEnum

class WhoAmIDTO(val email: String, val roles: List<String>) {
    companion object {
        fun fromUser(user: User) = WhoAmIDTO(
            email = user.email,
            roles = user.roles.map(RoleEnum::toString)
        )
    }
}
