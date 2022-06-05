package io.uvera.eobrazovanje.student.payments

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.shared.payment.dto.PaymentCreateDTO
import io.uvera.eobrazovanje.api.shared.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.common.repository.Payments
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.resolve
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.saveAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime

class StudentPaymentTests : ApplicationTest() {
    val sampleName = "Test name"
    val sampleMail = "Sample mail"
    val sampleAmount = BigDecimal(100.0)
    val sampleDate = LocalDateTime.now()

    val student = Student(
        transcriptNumber = "340-123123-299",
        identificationNumber = "SF-29/2019",
        currentYear = 3,
        changedPassword = true,
        balance = sampleAmount,
        user = User(
            firstName = sampleName,
            lastName = sampleName,
            email = sampleMail,
            password = "{noop}test",
            role = RoleEnum.STUDENT,
        ),
    )

    @BeforeEach
    fun beforeEachTest() {
        studentRepository.save(student)
    }

    @AfterEach
    fun afterEachTest() {
        paymentRepository.deleteAll()
        studentRepository.deleteAll()
    }

    @Test
    fun `test get one payment`() = paymentRepository {
        val payment = Payments(
            amount = BigDecimal(1000.0),
            depositedAt = sampleDate,
            student = student
        ).save()

        val idToString = payment.id.toString()
        val (body, response) = restTemplate.getForEntity<PaymentViewDTO>("/api/payment/$idToString").resolve()

        assert(response.statusCode == HttpStatus.OK)
        assert(body.id == payment.id)
        assert(body.amount.longValueExact() == payment.amount.longValueExact())
        assert(body.depositedAt.toLocalDate() == payment.depositedAt.toLocalDate())
        assert(body.student.id == payment.student.id)
    }

    @Test
    fun `test get payments by page`() = paymentRepository {
        listOf(1, 2, 3, 4).map {
            Payments(
                amount = BigDecimal(1000 + it),
                depositedAt = sampleDate,
                student = student
            )
        }.saveAll()
        val (body, _) = restTemplate.getForEntity<Page<PaymentViewDTO>>("/api/payment/paged/${student.id}?page=${1}&records=${2}")
            .resolve()
        assert(body.content.size == 2)
        assert(body.totalElements == 4L)
    }

    @Test
    fun `test create one payment`() {
        val (body, response) = restTemplate.postForEntity<PaymentViewDTO>(
            "/api/payment",
            PaymentCreateDTO(
                amount = sampleAmount,
                depositedAt = sampleDate,
                studentId = student.id
            )
        ).resolve()
        assert(response.statusCode == HttpStatus.OK)
        body.let {
            assert(it.amount.longValueExact() == sampleAmount.longValueExact())
            assert(it.depositedAt.toLocalDate() == sampleDate.toLocalDate())
            assert(it.student.id == student.id)
        }
    }

    @Test
    fun `test delete payment`() = paymentRepository {
        val payment = Payments(
            amount = BigDecimal(1000),
            depositedAt = sampleDate,
            student = student
        ).save()
        restTemplate.delete("/api/payment/${payment.id}")
        assert(count() == 0L)
    }
}
