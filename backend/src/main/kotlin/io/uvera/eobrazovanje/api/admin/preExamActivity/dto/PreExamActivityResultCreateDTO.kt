package io.uvera.eobrazovanje.api.admin.preExamActivity.dto

import org.springframework.validation.annotation.Validated
import java.util.UUID
import javax.validation.constraints.NotNull

@Validated
class PreExamActivityResultCreateDTO (
    @field:NotNull
    val preExamActivityId: UUID,

    @field:NotNull
    val score: Int,

    @field:NotNull
    val studentId: UUID
)