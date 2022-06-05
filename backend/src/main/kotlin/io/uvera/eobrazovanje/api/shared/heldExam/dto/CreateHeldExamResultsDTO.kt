package io.uvera.eobrazovanje.api.shared.heldExam.dto

import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.constraints.NotNull

@Validated
class CreateHeldExamResultsDTO(

    @field:NotNull
    var studentId: UUID,

    @field:NotNull
    var heldExamId: UUID,

    @field:NotNull
    var score: Int
)
