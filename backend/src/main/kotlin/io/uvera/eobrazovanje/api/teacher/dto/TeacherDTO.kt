package io.uvera.eobrazovanje.api.teacher.dto

import io.uvera.eobrazovanje.common.repository.TeacherType
import javax.validation.constraints.NotBlank

class TeacherDTO (

    @NotBlank
    val teacherType: TeacherType,

    @NotBlank
    var firstName: String,

    @NotBlank
    var lastName: String,

    @NotBlank
    var email: String,

    @NotBlank
    var password: String,

)