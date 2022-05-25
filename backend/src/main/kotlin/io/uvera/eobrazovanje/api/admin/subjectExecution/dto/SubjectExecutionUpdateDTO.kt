package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class SubjectExecutionUpdateDTO(

    @field:NotBlank
    var place: String,

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    var time: LocalTime,

    @field:NotBlank
    var weekDay: String,

    @field:NotNull
    var preExamActivityIds: List<UUID>,
)