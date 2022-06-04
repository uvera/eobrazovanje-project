package io.uvera.eobrazovanje.api.admin.heldExam.dto

import org.springframework.validation.annotation.Validated
import java.util.UUID
import javax.validation.constraints.NotNull

@Validated
class CreateHeldExamResultDTO(

    @field:NotNull
    var subjectExecutionId: UUID,

    @field:NotNull
    var examPeriodId: UUID,

    @field:NotNull
    var studentId: UUID,

    @field:NotNull
    var points: Int
)
