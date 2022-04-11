package io.uvera.eobrazovanje.api.admin.payment

import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentCreateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.common.repository.Payments
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("/api/payment")
@RestController
class PaymentController(protected val service: PaymentService) {
    val logger by loggerDelegate()

    @PreAuthorize("hasAnyRole('STUDENT, ADMIN')")
    @PostMapping
    fun createPayment(@Validated @RequestBody payment: PaymentCreateDTO): ResponseEntity<PaymentViewDTO> =
        service.createPayment(payment).ok

    @PreAuthorize("hasAnyRole('STUDENT, ADMIN')")
    @GetMapping("/paged/{id}")
    fun getPaymentsByStudentAndPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @PathVariable id: UUID
    ): ResponseEntity<Page<PaymentViewDTO>> {
        logger.info("Pagination ${Payments::class.simpleName} with params: { page $page, records: $records, studentId: $id}")
        return service.getPayments(page, records, id).ok
    }
}
