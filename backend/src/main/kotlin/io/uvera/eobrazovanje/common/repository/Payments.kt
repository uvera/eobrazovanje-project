package io.uvera.eobrazovanje.common.repository

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payments")
class Payments(

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    var amount: BigDecimal,

    @Column(name = "deposited_at", nullable = false)
    var depositedAt: LocalDateTime,

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()
