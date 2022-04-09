package io.uvera.eobrazovanje.security.configuration

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class RoleEnum {
    STUDENT,
    TEACHER,
    ADMIN;

    companion object {
        const val ROLE_PREFIX_VALUE = "ROLE_"

        @JsonCreator
        @JvmStatic
        fun fromString(value: String) = valueOf(value)
    }
}
