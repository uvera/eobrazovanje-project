package io.uvera.eobrazovanje.api.admin.payment.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Payments
import io.uvera.eobrazovanje.common.repository.Student
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@EntityView(Payments::class)
interface PaymentViewDTO {
    val id: UUID
    val amount: BigDecimal
    val depositedAt: LocalDateTime

    val student: StudentViewDTO

    @EntityView(Student::class)
    interface StudentViewDTO {
        val id: UUID
    }
}
