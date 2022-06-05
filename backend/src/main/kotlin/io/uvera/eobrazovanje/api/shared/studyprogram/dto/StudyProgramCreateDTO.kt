package io.uvera.eobrazovanje.api.shared.studyprogram.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Validated
class StudyProgramCreateDTO(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val codeName: String,

    @field:NotEmpty
    @field:JsonDeserialize
    var subjects: List<UUID>,
)
