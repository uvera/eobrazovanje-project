package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class SubjectExecutionUpdateDTO(

    @field:NotBlank
    var place: String,

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var time: LocalDateTime,

    @field:NotNull
    var preExamActivityIds: List<UUID>,
)