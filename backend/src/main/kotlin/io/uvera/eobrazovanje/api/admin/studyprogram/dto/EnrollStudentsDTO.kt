package io.uvera.eobrazovanje.api.admin.studyprogram.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.constraints.NotEmpty

@Validated
class EnrollStudentsDTO (
    @field:NotEmpty
    @field:JsonDeserialize
    val studentIds: List<UUID>
    )