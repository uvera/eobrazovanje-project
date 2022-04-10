package io.uvera.eobrazovanje.api.admin.teacher

import io.uvera.eobrazovanje.api.admin.teacher.dto.*
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO_.teacherType
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.TeacherRepository
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TeacherService(
    protected val repo: TeacherRepository,
) {

    fun getTeacher(id: UUID): TeacherResponseDTO =
        repo.findByIdAsDto(id) ?: notFoundById<Teacher>(id)

    fun getTeachersByPage(page: Int, records: Int): Page<TeacherResponseDTOImpl> = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAll(req).map {
            TeacherResponseDTOImpl(
                it.teacherType,
                TeacherResponseDTOUserDTOImpl(
                    it.user.email,
                    it.user.firstName,
                    it.user.lastName,
                    it.user.roles
                )
            )
        }
    }

    fun createTeacher(dto: TeacherDTO) = repo {
        teacherDTOToEntity(dto).save().let {
            findByIdAsDto(it.id) ?: notFoundById<User>(it.id)
        }
    }

    fun updateTeacher(id: UUID, dto: TeacherUpdateDTO): TeacherResponseDTO = repo {
        val teacher = findByIdOrNull(id) ?: notFoundById<Teacher>(id)
        teacher.update {
            teacherType = dto.teacherType
            user.tap {
                firstName = dto.firstName
                lastName = dto.lastName
            }
        }
        return@repo findByIdAsDto(teacher.id) ?: notFoundById<Teacher>(teacher.id)
    }

    fun deleteTeacher(id: UUID) = repo {
        if (!existsById(id)) notFoundById<Teacher>(id)
        return@repo deleteById(id)
    }
}

fun teacherDTOToEntity(dto: TeacherDTO): Teacher {
    return Teacher(
        teachingSince = LocalDate.now(),
        teacherType = dto.teacherType,
        user = User(dto.firstName, dto.lastName, dto.email, dto.password, true)
    )
}
