package io.uvera.eobrazovanje.admin.student

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.student.dto.*
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.TeacherType
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.error.dto.ApiError
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.save
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotBlank

class AdminStudentTests : ApplicationTest() {
    val sampleName = "Test name"
    val sampleMail = "Sample mail"

    @BeforeEach
    fun beforeEachTest() {
        studentRepository.deleteAll()
    }

    @Test
    fun `get students paged`() = studentRepository {
        listOf(1, 2, 3, 4).map {
            Student(
                user = User(
                    firstName = sampleName + it,
                    lastName = sampleName + it,
                    email = sampleMail + it,
                    password = "{noop}test",
                    roles = mutableListOf(RoleEnum.STUDENT),
                ),
                transcriptNumber = "1252950-21$it",
                identificationNumber = "2415215$it",
                currentYear = 2,
                changedPassword = false,
                balance = BigDecimal(200),

            )
        }.forEach {
            it.save()
        }
        val res = restTemplate.getForEntity<Page<StudentViewDTOImpl>>("/api/admin/student/paged?page=${1}&records=${2}")
        val body = res.body!!
        assert(body.content.size == 2)
        assert(body.totalElements == 4L)
    }

    @Test
    fun `get one student`() {
        var student = Student(
            transcriptNumber = "1234",
            identificationNumber = "12313",
            currentYear = 1231,
            changedPassword = true,
            balance = BigDecimal(200),
            user = User(
                firstName = "Marko",
                lastName = "Markovic",
                email = "marko@marko.com",
                password = "{noop}test",
                roles = mutableListOf(RoleEnum.STUDENT),
            )
        )
        student = studentRepository.save(student)
        val response = restTemplate.getForEntity<StudentViewDTOImpl>("/api/admin/student/${student.id}")
        val body = response.body!!
        assert(response.statusCode == HttpStatus.OK)
        assert(body.id == student.id)
    }

    @Test
    fun `test creation with one student in list`() {
        val response = restTemplate.postForEntity<List<CreatedStudentDTO>>(
            "/api/admin/student", AdminCreateStudentsDTO(
                data = listOf(
                    AdminCreateStudentDTO(
                        transcriptNumber = "1234",
                        identificationNumber = "12313",
                        currentYear = 1231,
                        firstName = "Marko",
                        lastName = "Petrovic",
                        email = "marko@petrovic.com"
                    )
                )
            )
        )
        assert(response.statusCode == HttpStatus.OK)
    }

    @Test
    fun `test creation failure`() {
        val response = restTemplate.postForEntity<ApiError>(
            "/api/admin/student", AdminCreateStudentsDTO(
                data = listOf(
                    AdminCreateStudentDTO(
                        transcriptNumber = "",
                        identificationNumber = "",
                        currentYear = 1231,
                        firstName = "",
                        lastName = "",
                        email = ""
                    )
                )
            )
        )
        assert(response.statusCode == HttpStatus.BAD_REQUEST)
        assert(response.body!!.errors.map { it.code }.any { it == NotBlank::class.simpleName.toString() })
    }
}
