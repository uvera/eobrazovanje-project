package io.uvera.eobrazovanje.api.admin.payment.dto

import io.uvera.eobrazovanje.common.repository.Student
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class PaymentCreateDTO (
    var amount: BigDecimal,
    var depositedAt: LocalDateTime,
    var studentId: UUID,
)
