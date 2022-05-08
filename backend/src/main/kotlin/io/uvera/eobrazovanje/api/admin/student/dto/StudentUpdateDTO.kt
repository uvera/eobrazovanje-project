package io.uvera.eobrazovanje.api.admin.student.dto

import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
class StudentUpdateDTO(
    @field:NotNull
    @field:NotBlank
    val firstName: String,

    @field:NotNull
    @field:NotBlank
    val lastName: String,

    @field:NotNull
    @field:NotBlank
    val transcriptNumber: String,

    @field:NotNull
    @field:NotBlank
    val identificationNumber: String,
)
