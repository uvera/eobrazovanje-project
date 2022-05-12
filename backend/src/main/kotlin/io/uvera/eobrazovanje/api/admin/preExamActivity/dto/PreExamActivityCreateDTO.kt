package io.uvera.eobrazovanje.api.admin.preExamActivity.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class PreExamActivityCreateDTO(
    @field:NotBlank
    val name: String,

    @field:NotNull
    val points: Int,
)