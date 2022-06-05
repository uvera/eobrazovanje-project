package io.uvera.eobrazovanje.admin.studyprogram

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.shared.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.shared.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.common.repository.StudyProgram
import io.uvera.eobrazovanje.common.repository.Subject
import io.uvera.eobrazovanje.resolve
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.reload
import io.uvera.eobrazovanje.util.extensions.saveAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

class AdminStudyProgramTest : ApplicationTest() {

    @BeforeEach
    fun deleteAll() {
        subjectRepository.deleteAll()
        studyProgramRepository.deleteAll()
    }

    @Test
    fun `test create one study program with subjects`() {
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
        val (body, response) = restTemplate.postForEntity<StudyProgramViewDTO>(
            "/api/admin/study-program",
            StudyProgramCreateDTO(
                name = "Study Program 1",
                codeName = "SF",
                subjects = subjects.map { it.id }
            )
        ).resolve()

        assert(response.statusCode == HttpStatus.OK)
        assert(body.subjects.map { it.id } == subjects.map { it.id })
        assert(body.name == "Study Program 1")
        assert(body.codeName == "SF")
    }

    @Test
    fun `get one study program`() {
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
        val studyProgram = studyProgramRepository.save(
            StudyProgram(
                codeName = "SF",
                name = "Uveric",
                subjects = subjects.toMutableList()
            )
        )
        subjects.forEach {
            it.studyProgram = studyProgram
            subjectRepository.save(it)
        }
        val (body, response) = restTemplate.getForEntity<StudyProgramViewDTO>("/api/admin/study-program/${studyProgram.id}")
            .resolve()
        assert(response.statusCode == HttpStatus.OK)
        assert(body.id == studyProgram.id)
        assert(body.subjects.size == subjects.size)
    }

    @Test
    fun `update one study program`() {
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
        val studyProgram = studyProgramRepository.save(
            StudyProgram(
                codeName = "SF",
                name = "Uveric",
                subjects = subjects.toMutableList()
            )
        )
        subjects.forEach {
            it.studyProgram = studyProgram
            subjectRepository.save(it)
        }
        restTemplate.put(
            "/api/admin/study-program/${studyProgram.id}",
            StudyProgramCreateDTO(
                name = "Study Program 2",
                codeName = "SG",
                subjects = subjects.map { it.id }.dropLast(1)
            )
        )
        studyProgramRepository {
            studyProgram.reload.let {
                assert(it.codeName == "SG")
                assert(it.name == "Study Program 2")
            }
        }
    }

    @Test
    fun `delete one study program`() {
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
        val studyProgram = studyProgramRepository.save(
            StudyProgram(
                codeName = "SF",
                name = "Uveric",
                subjects = subjects.toMutableList()
            )
        )
        subjects.forEach {
            it.studyProgram = studyProgram
            subjectRepository.save(it)
        }
        studyProgramRepository {
            restTemplate.delete("/api/admin/study-program/${studyProgram.id}")
            assert(count() == 0L)
        }
    }
}
