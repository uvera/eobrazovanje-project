package io.uvera.eobrazovanje.api.admin.subject.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
class SubjectCreateDTO(
    @field:NotBlank
    val espb: Int,

    @field:NotBlank
    val name: String,
    
    @field:NotBlank
    var year: Int,
)