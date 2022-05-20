package io.uvera.eobrazovanje.api.admin.payment

import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentCreateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentUpdateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.common.repository.Payments
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("/api/payment")
@RestController
class PaymentController(protected val service: PaymentService) {
    val logger by loggerDelegate()

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @PostMapping
    fun createPayment(@Validated @RequestBody payment: PaymentCreateDTO): ResponseEntity<PaymentViewDTO> =
        service.createPayment(payment).ok

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/{id}")
    fun getPayment(@PathVariable("id") paymentID: UUID) = service.getPayment(paymentID).ok

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/paged")
    fun getPaymentsByStudentAndPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @RequestParam(value = "id", required = true) id: UUID
    ): ResponseEntity<Page<PaymentViewDTO>> {
        logger.info("Pagination ${Payments::class.simpleName} with params: { page $page, records: $records, studentId: $id}")
        return service.getPayments(page, records, id).ok
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/student/paged")
    fun getPaymentsByStudentEmailAndPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @RequestParam(value = "email", required = true) email: String
    ): ResponseEntity<Page<PaymentViewDTO>> {
        logger.info("Pagination ${Payments::class.simpleName} with params: { page $page, records: $records, studentEmail: $email}")
        return service.getStudentPaymentsByEmail(page, records, email).ok
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @PutMapping("/{id}")
    fun updatePayment(
        @PathVariable("id") paymentId: UUID,
        @Validated @RequestBody payment: PaymentUpdateDTO
    ): ResponseEntity<PaymentViewDTO> = service.updatePayment(paymentId, payment).ok

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @DeleteMapping("/{id}")
    fun deletePayment(
        @PathVariable("id") paymentId: UUID
    ): AnyResponseEntity = service.deletePayment(paymentId).let { emptyOk }
}
