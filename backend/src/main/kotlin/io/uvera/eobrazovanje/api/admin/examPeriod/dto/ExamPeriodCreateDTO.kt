package io.uvera.eobrazovanje.api.admin.examPeriod.dto

import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class ExamPeriodCreateDTO(
    @field:NotBlank
    var name: String,

    @field:NotBlank
    var startDate: LocalDate,

    @field:NotBlank
    var endDate: LocalDate,

    @field:NotNull
    var subjectExecutionIds: List<UUID>

)