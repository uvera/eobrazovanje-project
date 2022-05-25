package io.uvera.eobrazovanje.api.admin.student.dto

import io.uvera.eobrazovanje.constraint.StudentTranscriptNumberExists
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

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
