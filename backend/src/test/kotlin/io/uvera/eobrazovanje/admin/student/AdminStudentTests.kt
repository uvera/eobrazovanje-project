package io.uvera.eobrazovanje.admin.student

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentDTO
import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import io.uvera.eobrazovanje.api.admin.student.dto.CreatedStudentDTO
import io.uvera.eobrazovanje.error.dto.ApiError
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import javax.validation.constraints.NotBlank

class AdminStudentTests : ApplicationTest() {

    @Test
    fun `test creation with one student in list`() {
        val response = restTemplate.postForEntity<List<CreatedStudentDTO>>(
            "/api/admin/student",
            entityWithAuth(
                AdminCreateStudentsDTO(
                    data =
                    listOf(
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
        )
        assert(response.statusCode == HttpStatus.OK)
    }

    @Test
    fun `test creation failure`() {
        val response = restTemplate.postForEntity<ApiError>(
            "/api/admin/student",
            entityWithAuth(
                AdminCreateStudentsDTO(
                    data =
                    listOf(
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
        )
        assert(response.statusCode == HttpStatus.BAD_REQUEST)
        assert(response.body!!.errors.map { it.code }.any { it == NotBlank::class.simpleName.toString() })
    }
}
