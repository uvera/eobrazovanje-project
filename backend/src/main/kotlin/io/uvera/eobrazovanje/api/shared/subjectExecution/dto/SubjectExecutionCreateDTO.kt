package io.uvera.eobrazovanje.api.shared.subjectExecution.dto

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class SubjectExecutionCreateDTO(

    @field:NotBlank
    var place: String,

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    var time: LocalTime,

    @field:NotBlank
    var weekDay: String,

    @field:NotNull
    var subjectId: UUID,

    @field:NotNull
    var preExamActivityIds: List<UUID>,

    @field:NotNull
    var teacherIds: List<UUID>

)
