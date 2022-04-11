package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class SubjectExecutionCreateDTO (

    @field:NotBlank
    var place: String,

    @field:DateTimeFormat
    var time: LocalDateTime,

    @field:NotBlank
    var subjectId: UUID,

    @field:NotNull
    var preExamActivityIds: List<UUID>
)