package io.uvera.eobrazovanje.api.admin.heldExam.dto

import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

@Validated
class HeldExamResultViewDTO (

    @field:NotNull
    var subject: String,

    @field:NotNull
    var date: LocalDate,

    @field:NotNull
    var score: Int
)