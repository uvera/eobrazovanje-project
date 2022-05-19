package io.uvera.eobrazovanje.api.admin.student.dto

import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class AdminCreateStudentsDTO(
    @field:NotEmpty
    @field:Valid
    val data: List<AdminCreateStudentDTO>,
)
