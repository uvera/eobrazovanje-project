package io.uvera.eobrazovanje.admin.examperiod

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.common.repository.Subject
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import io.uvera.eobrazovanje.resolve
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.saveAll
import liquibase.pro.packaged.it
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime

class ExamPeriodTests : ApplicationTest() {

    @BeforeEach
    fun beforeEachTest() {
        subjectRepository.deleteAll()
        examPeriodRepository.deleteAll()
    }

    @Test
    fun `test create one exam period`() {
        val subjects = subjectRepository {
            listOf(
                Subject(
                    espb = 40,
                    name = "XML",
                    year = 2
                ),
                Subject(
                    espb = 69,
                    name = "Nije XML",
                    year = 2
                )
            ).saveAll()
        }
        val subjectEx = subjectExecutionRepository {
            listOf(
                SubjectExecution(
                    place = "Temerin",
                    time = LocalDateTime.now(),
                    subject = subjects[0]
                ),
                SubjectExecution(
                    place = "Barake iza detelinare",
                    time = LocalDateTime.now(),
                    subject = subjects[1]
                )
            ).saveAll()
        }
        val (body, response) = restTemplate.postForEntity<ExamPeriodViewDTO>(
            "/api/admin/exam-period",
            ExamPeriodCreateDTO(
                name = "Exam period 1",
                startDate = LocalDate.now(),
                endDate = LocalDate.now(),
                subjectExecutionIds = subjectEx.map { it.id }
            )
        ).resolve()

        println(body.subjectExecutions)
        assert(response.statusCode == HttpStatus.OK)
        assert(body.subjectExecutions.map { it.id } == subjectEx.map { it.id })
        assert(body.name == "Exam period 1")
    }
}
