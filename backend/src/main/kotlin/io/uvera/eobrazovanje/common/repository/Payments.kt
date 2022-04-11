package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
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

interface PaymentRepository : JpaSpecificationRepository<Payments, UUID> {
    @Query("select p from Payments p where p.id = :id")
    fun findByIdAsDto(id: UUID): PaymentViewDTO?

    @Query("select p from Payments p where p.student.id = :id")
    fun findAllByStudentId(id: UUID, pageable: Pageable): Page<PaymentViewDTO>
}
