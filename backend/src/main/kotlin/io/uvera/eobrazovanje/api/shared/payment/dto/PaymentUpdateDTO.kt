package io.uvera.eobrazovanje.api.shared.payment.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

class PaymentUpdateDTO(
    @field:NotNull
    var amount: BigDecimal,
    @field:NotNull
    var depositedAt: LocalDateTime,
)
