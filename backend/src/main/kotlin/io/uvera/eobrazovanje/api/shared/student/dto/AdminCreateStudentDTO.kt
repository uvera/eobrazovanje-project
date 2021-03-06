package io.uvera.eobrazovanje.api.shared.student.dto

import io.uvera.eobrazovanje.constraint.StudentTranscriptNumberExists
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
class AdminCreateStudentDTO(
    @field:NotBlank
    val transcriptNumber: String,

    @field:NotBlank
    @StudentTranscriptNumberExists
    val identificationNumber: String,

    @field:NotBlank
    val currentYear: String,

    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

    @field:NotBlank
    var email: String,

    @field:NotBlank
    var studyProgramCode: String
)
