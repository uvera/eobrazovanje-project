package io.uvera.eobrazovanje.api.admin.student.dto

import io.uvera.eobrazovanje.constraint.StudentTranscriptNumberExists
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class AdminCreateStudentDTO(
    @field:NotBlank
    val transcriptNumber: String,

    @field:NotBlank
    @StudentTranscriptNumberExists
    val identificationNumber: String,

    @field:NotNull
    val currentYear: Int,

    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

    @field:NotBlank
    var email: String,
)
