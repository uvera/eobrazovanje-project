package io.uvera.eobrazovanje.student.payments

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentCreateDTO
import io.uvera.eobrazovanje.api.admin.payment.dto.PaymentViewDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.common.repository.Payments
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.saveAll
import org.junit.jupiter.api.AfterEach
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.data.domain.Page
import java.math.BigDecimal
import java.time.LocalDateTime

class StudentPaymentTests : ApplicationTest() {
    val sampleName = "Test name"
    val sampleMail = "Sample mail"
    val sampleAmount = BigDecimal(100)
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
            roles = mutableListOf(RoleEnum.STUDENT),
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
            amount = BigDecimal(1000),
            depositedAt = sampleDate,
            student = student
        )
        save(payment)

        val idToString = payment.id.toString()
        val response = restTemplate.getForEntity<PaymentViewDTO>("/api/payments/$idToString")
        val body = response.body!!
        assert((response.statusCode == HttpStatus.OK))
        assert(body.id == payment.id)
        assert(body.amount == payment.amount)
        assert(body.depositedAt == payment.depositedAt)
        assert(body.student.id == payment.student.id)
    }

    @Test
    fun `test get payments by page`() = paymentRepository {
        val payments = listOf(1, 2, 3, 4).map {
            Payments(
                amount = BigDecimal(1000 + it),
                depositedAt = sampleDate,
                student = student
            )
        }.saveAll()
        val res = restTemplate.getForEntity<Page<PaymentViewDTO>>("/api/payments/paged/${student.id}?page=${1}&records=${2}")
        val body = res.body!!
        assert(body.content.size == 2)
        assert(body.totalElements == 4L)
    }

    @Test
    fun `test create one payment`() {
        val response = restTemplate.postForEntity<PaymentViewDTO>(
            "/api/payments",
            PaymentCreateDTO(
                amount = BigDecimal(1000),
                depositedAt = sampleDate,
                studentId = student.id
            )
        )
        assert(response.statusCode == HttpStatus.OK)
        val body = response.body!!
        body.let {
            assert(it.amount == sampleAmount)
            assert(it.depositedAt == sampleDate)
            assert(it.student.id == student.id)
        }
    }

    @Test
    fun `test delete payment`() = paymentRepository{
        val payment = Payments(
            amount = BigDecimal(1000),
            depositedAt = sampleDate,
            student = student
        )
        save(payment)
        val res = restTemplate.delete("/api/payments/${payment.id}")
        assert(count() == 1L)
    }
}