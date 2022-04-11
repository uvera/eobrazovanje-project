package io.uvera.eobrazovanje.admin.subject

import io.uvera.eobrazovanje.ApplicationTest
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectCreateDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Subject
import io.uvera.eobrazovanje.resolve
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.reload
import io.uvera.eobrazovanje.util.extensions.save
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

class AdminSubjectTest : ApplicationTest() {

    @BeforeEach
    fun beforeEachTest() {
        subjectRepository.deleteAll()
    }

    @Test
    fun `test getting one subject`() = subjectRepository {
        val subject = Subject(
            espb = 10,
            name = "Test1",
            year = 1,
        ).save()

        val (body, response) = restTemplate.getForEntity<SubjectViewDTO>("/api/admin/subject/${subject.id}").resolve()
        assert(response.statusCode == HttpStatus.OK)
        assert(body.espb == 10)
        assert(body.name == "Test1")
        assert(body.year == 1)
    }

    @Test
    fun `test creation one subject`() {

        val (body, response) = restTemplate.postForEntity<SubjectViewDTO>(
            "/api/admin/subject",
            SubjectCreateDTO(
                espb = 1111,
                name = "fdsafdsafdsa",
                year = 1
            )
        ).resolve()

        assert(response.statusCode == HttpStatus.OK)
        assert(body.espb == 1111)
        assert(body.name == "fdsafdsafdsa")
        assert(body.year == 1)
        assert(subjectRepository.count() == 1L)
    }

    @Test
    fun `update one subject`() = subjectRepository {
        val subject = Subject(
            espb = 10,
            name = "Test1",
            year = 1,
        ).save()

        restTemplate.put(
            "/api/admin/subject/${subject.id}",
            SubjectCreateDTO(
                espb = 11,
                name = "Test2",
                year = 2
            )
        )
        subject.reload.let {
            assert(it.espb == 11)
            assert(it.name == "Test2")
            assert(it.year == 2)
        }
    }

    @Test
    fun `delete one subject`() = subjectRepository {
        val subject = Subject(
            espb = 10,
            name = "Test1",
            year = 1,
        ).save()

        restTemplate.delete("/api/admin/subject/${subject.id}")
        assert(count() == 0L)
    }
}
