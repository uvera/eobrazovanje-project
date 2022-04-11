package io.uvera.eobrazovanje.api.admin.payment.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PaymentCreateDTO(
    @field:NotNull
    var amount: BigDecimal,
    @field:NotNull
    var depositedAt: LocalDateTime,
    @field:NotNull
    var studentId: UUID,
)
