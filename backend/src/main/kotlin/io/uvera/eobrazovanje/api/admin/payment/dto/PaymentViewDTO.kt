package io.uvera.eobrazovanje.api.admin.payment.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Payments
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@EntityView(Payments :: class)
interface PaymentViewDTO {
    var id: UUID
    var amount: BigDecimal
    var depositedAt: LocalDateTime
    var studentId: UUID
}
