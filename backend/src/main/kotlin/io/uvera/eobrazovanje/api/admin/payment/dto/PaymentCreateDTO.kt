package io.uvera.eobrazovanje.api.admin.payment.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class PaymentCreateDTO(
    var amount: BigDecimal,
    var depositedAt: LocalDateTime,
    var studentId: UUID,
)
