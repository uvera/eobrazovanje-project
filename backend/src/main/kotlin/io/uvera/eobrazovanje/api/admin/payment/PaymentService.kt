package io.uvera.eobrazovanje.api.admin.payment
import io.uvera.eobrazovanje.api.admin.payment.dto.StudentPaymentCreateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentCreateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentUpdateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.update
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentService(
    protected val paymentRepo: PaymentRepository,
    protected val studentRepo: StudentRepository
) {
    fun createPayment(paymentDTO: PaymentCreateDTO): PaymentViewDTO = paymentRepo {
        // TODO add addition of funds to the balance of students

        getPayment(paymentDTOToEntity(paymentDTO).save().id)
    }

    fun getPayment(paymentId: UUID): PaymentViewDTO =
        paymentRepo.findByIdAsDto(paymentId) ?: notFoundById<Payments>(paymentId)
    

    fun updatePayment(id: UUID, dto: PaymentUpdateDTO): PaymentViewDTO = paymentRepo {
        val dbPayments = findByIdOrNull(id) ?: notFoundById<Payments>(id)
        dbPayments.update {
            amount = dto.amount
            depositedAt = dto.depositedAt
        }
        return@paymentRepo findByIdAsDto(id) ?: notFoundById<Payments>(id)
    }

    fun deletePayment(id: UUID) = paymentRepo {
        if (!existsById(id)) notFoundById<Payments>(id)
        return@paymentRepo deleteById(id)
    }

    fun getPayments(page: Int, records: Int, id: UUID): Page<PaymentViewDTO> = paymentRepo {
        val req = PageRequest.of(page - 1, records)

        return@paymentRepo findAllByStudentId(id, req)
    }

    fun getStudentPaymentsByEmail(page: Int, records: Int, email: String): Page<PaymentViewDTO> = paymentRepo {
        val req = PageRequest.of(page - 1, records)
    
        return@paymentRepo findAllByStudentEmail(email, req)
    }


    fun paymentDTOToEntity(dto: PaymentCreateDTO): Payments {
        return Payments(
            amount = dto.amount,
            depositedAt = dto.depositedAt,
            student = studentRepo.findByIdOrNull(dto.studentId) ?: notFoundById<Student>(dto.studentId)
        )
    }
}
