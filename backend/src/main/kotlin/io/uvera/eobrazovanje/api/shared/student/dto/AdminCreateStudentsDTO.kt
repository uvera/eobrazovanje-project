package io.uvera.eobrazovanje.api.shared.student.dto

import javax.validation.Valid
import javax.validation.constraints.NotEmpty

class AdminCreateStudentsDTO(
    @field:NotEmpty
    @field:Valid
    val data: List<AdminCreateStudentDTO>,
)
