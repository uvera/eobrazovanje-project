package io.uvera.eobrazovanje.admin.student

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentDTO
import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class AdminStudentTests : ApplicationTest() {

    @Test
    fun `test creation with one student in list`() {
        mockMvc.post("/admin/student") {
            withAdminAuthorizationHeader()
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(
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
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
        assert(studentRepository.count() == 1L)
    }

    @Test
    fun `test creation failure`() {
        mockMvc.post("/admin/student") {
            withAdminAuthorizationHeader()
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(
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
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
            content { string(containsString("error")) }
        }
        assert(studentRepository.count() == 0L)
    }
}
