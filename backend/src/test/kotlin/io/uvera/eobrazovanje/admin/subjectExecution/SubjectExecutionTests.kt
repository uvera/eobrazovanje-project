package io.uvera.eobrazovanje.admin.subjectExecution

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.PreExamActivity
import io.uvera.eobrazovanje.common.repository.Subject
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.saveAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class SubjectExecutionTests : ApplicationTest() {

    @BeforeEach
    fun beforeEachTest() {
        subjectRepository.deleteAll()
        subjectExecutionRepository.deleteAll()
    }

    @Test
    fun `create one subject execution`() {
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
        val prexam = preExamActivityRepository {
            listOf(
                PreExamActivity(
                    name = "Obaveza 1"
                ),
                PreExamActivity(
                    name = "Obaveza 2"
                )
            ).saveAll()
        }
        val response = restTemplate.postForEntity<SubjectExecutionViewDTO>(
            "/api/admin/subject-execution",
            SubjectExecutionCreateDTO(
                place = "Barake iza detelinare",
                time = LocalDateTime.now(),
                subjectId = subjects[1].id,
                preExamActivityIds = prexam.map { it.id }
            )
        )

        val body = response.body!!
        assert(body.preExamActivities.size == 2)
        assert(response.statusCode == HttpStatus.OK)
        assert(body.place == "Barake iza detelinare")
    }
}
