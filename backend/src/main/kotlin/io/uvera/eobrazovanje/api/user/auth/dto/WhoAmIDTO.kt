package io.uvera.eobrazovanje.api.user.auth.dto

import io.uvera.eobrazovanje.common.repository.User

class WhoAmIDTO(val email: String, val role: String, val obj: Any?) {
    companion object {
        fun fromUser(user: User, underObject: Any?) = WhoAmIDTO(
            email = user.email,
            role = user.role.toString(),
            obj = underObject
        )
    }
}
