package io.uvera.eobrazovanje.api.admin.teacher.dto

import io.uvera.eobrazovanje.common.repository.TeacherType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class TeacherUpdateDTO(

    @field:NotNull
    val teacherType: TeacherType,

    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

)
