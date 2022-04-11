package io.uvera.eobrazovanje.api.admin.subject.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class SubjectCreateDTO(
    @field:NotNull
    val espb: Int,

    @field:NotBlank
    val name: String,

    @field:NotNull
    var year: Int,
)
