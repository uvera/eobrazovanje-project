package io.uvera.eobrazovanje.api.shared.teacher.dto

import io.uvera.eobrazovanje.common.repository.TeacherType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class TeacherDTO(

    @field:NotNull
    val teacherType: TeacherType,

    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

    @field:NotBlank
    var email: String,

    @field:NotBlank
    var password: String,

)
