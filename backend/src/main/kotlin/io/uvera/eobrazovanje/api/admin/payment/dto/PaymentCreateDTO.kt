package io.uvera.eobrazovanje.api.admin.payment.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.validation.constraints.NotBlank

class PaymentCreateDTO(
    @field:NotBlank
    var amount: BigDecimal,
    @field:NotBlank
    var depositedAt: LocalDateTime,
    @field:NotBlank
    var studentId: UUID,
)
