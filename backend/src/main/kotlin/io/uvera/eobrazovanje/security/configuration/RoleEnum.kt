package io.uvera.eobrazovanje.security.configuration

enum class RoleEnum {
    STUDENT,
    TEACHER,
    ADMIN;

    companion object {
        const val ROLE_PREFIX_VALUE = "ROLE_"
    }
}
