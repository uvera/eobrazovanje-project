package io.uvera.eobrazovanje.api.shared.examPeriod.dto

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class ExamPeriodCreateDTO(
    @field:NotBlank
    var name: String,

    @field:NotNull
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var startDate: LocalDate,

    @field:NotNull
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var endDate: LocalDate,

    @field:NotNull
    var subjectExecutionIds: List<UUID>

)
