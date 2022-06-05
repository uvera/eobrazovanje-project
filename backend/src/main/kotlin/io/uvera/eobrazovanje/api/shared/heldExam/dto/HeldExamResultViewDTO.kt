package io.uvera.eobrazovanje.api.shared.heldExam.dto

import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import javax.validation.constraints.NotNull

@Validated
class HeldExamResultViewDTO(

    @field:NotNull
    var subject: String,

    @field:NotNull
    var date: LocalDate,

    @field:NotNull
    var score: Int
)
