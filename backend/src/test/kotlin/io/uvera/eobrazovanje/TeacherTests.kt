package io.uvera.eobrazovanje

import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.TeacherType
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.error.exception.EntityNotFoundException
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.EmptyObject
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.reload
import io.uvera.eobrazovanje.util.extensions.save
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDate

class TeacherTests : ApplicationTest() {
    val sampleName = "Test name"
    val sampleMail = "Sample mail"
    val roles = mutableListOf(RoleEnum.TEACHER)
    val teacherType = TeacherType.PROFESSOR

    @BeforeEach
    fun beforeEachTest() = teacherRepository.deleteAll()

    @Test
    fun `test get teacher`() = teacherRepository {
        val teacher = Teacher(
            teachingSince = LocalDate.now(), teacherType = teacherType,
            user = User(
                firstName = sampleName,
                lastName = sampleName,
                email = sampleMail,
                password = "{noop}test",
                roles = mutableListOf(RoleEnum.TEACHER),
            )
        )
        save(teacher)

        val idToString = teacher.id.toString()
        val body = restTemplate.getForEntity<TeacherResponseDTO>("/api/teacher/$idToString").body!!
        with(body) {
            assert(this.teacherType == TeacherType.PROFESSOR)
            with(user) {
                assert(firstName == sampleName)
                assert(lastName == sampleName)
                assert(this.roles == this@TeacherTests.roles)
            }
        }
    }

    @Test
    fun `create teacher`() {
        val response = restTemplate.postForEntity<TeacherResponseDTO>(
            "/api/teacher",
            TeacherDTO(
                teacherType = teacherType,
                firstName = sampleName,
                lastName = sampleName,
                email = sampleMail,
                password = "Test123",
            )
        )
        assert(response.statusCode == HttpStatus.OK)
        val body = response.body!!
        body.let {
            assert(it.teacherType == teacherType)
            assert(it.user.firstName == sampleName)
            assert(it.user.lastName == sampleName)
            assert(it.user.email == sampleMail)
        }
    }

    @Test
    fun `update teacher`() = teacherRepository {
        val teacher = Teacher(
            teachingSince = LocalDate.now(), teacherType = teacherType,
            user = User(
                firstName = sampleName,
                lastName = sampleName,
                email = sampleMail,
                password = "{noop}test",
                roles = mutableListOf(RoleEnum.TEACHER),
            )
        )
        teacher.save()
        restTemplate.put(
            "/api/teacher/${teacher.id}",
            TeacherUpdateDTO(
                teacherType = TeacherType.ASSISTANT,
                firstName = "update",
                lastName = "update",
            )
        )

        teacher.reload.let {
            assert(it.teacherType == TeacherType.ASSISTANT)
            assert(it.user.firstName == "update")
            assert(it.user.lastName == "update")
        }
    }

    @Test
    fun `delete teacher`() = teacherRepository {
        val teacher = Teacher(
            teachingSince = LocalDate.now(), teacherType = teacherType,
            user = User(
                firstName = sampleName,
                lastName = sampleName,
                email = sampleMail,
                password = "{noop}test",
                roles = mutableListOf(RoleEnum.TEACHER),
            )
        )
        save(teacher)
        val res = restTemplate.exchange<EmptyObject>("/api/teacher/${teacher.id}", HttpMethod.DELETE)
        assert(res.statusCode == HttpStatus.OK)
        assert(count() == 0L)
        assertThrows<EntityNotFoundException> {
            teacher.reload
        }
        Unit
    }

    @Test
    fun `get teachers paged`() = teacherRepository {
        listOf(1, 2, 3, 4).map {
            Teacher(
                teachingSince = LocalDate.now(), teacherType = teacherType,
                user = User(
                    firstName = sampleName + it,
                    lastName = sampleName + it,
                    email = sampleMail + it,
                    password = "{noop}test",
                    roles = mutableListOf(RoleEnum.TEACHER),
                )
            )
        }.forEach {
            it.save()
        }
        val res = restTemplate.getForEntity<Page<TeacherResponseDTO>>("/api/teacher/paged?page=${1}&records=${2}")
        val body = res.body!!
        assert(body.content.size == 2)
        assert(body.totalElements == 4L)
    }
}
