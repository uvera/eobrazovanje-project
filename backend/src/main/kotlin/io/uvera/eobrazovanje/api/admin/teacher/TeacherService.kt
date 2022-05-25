package io.uvera.eobrazovanje.api.admin.teacher

import io.uvera.eobrazovanje.api.admin.teacher.dto.*
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class TeacherService(
    protected val repo: TeacherRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
    protected val profEnrolRepo: SubjectProfessorEnrollmentRepository,
    protected val paEc: PasswordEncoder
) {

    fun getTeacher(id: UUID): TeacherResponseDTO =
        repo.findByIdAsDto(id) ?: notFoundById<Teacher>(id)

    fun getTeachersByPage(page: Int, records: Int): Page<TeacherResponseDTO> = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    fun getAllTeachers(): Any = repo {
        return@repo findAllTeachers()
    }

    @Transactional
    fun createTeacher(dto: TeacherDTO) = repo {
        teacherDTOToEntity(dto).save().let {
            findByIdAsDto(it.id) ?: notFoundById<User>(it.id)
        }
    }

    @Transactional
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

    @Transactional
    fun deleteTeacher(id: UUID) = repo {
        if (!existsById(id)) notFoundById<Teacher>(id)
        return@repo deleteById(id)
    }

    @Transactional
    fun addTeacherToSubject(dto: TeacherSubjectExecutionDTO) {
        val teacher = repo.findByIdOrNull(dto.teacherId) ?: notFoundById<Teacher>(dto.teacherId)
        dto.subjectExecutionIds.forEach {
            val subject = subjectExRepo.findByIdOrNull(it) ?: notFoundById<SubjectExecution>(it)
            val enrollment = SubjectProfessorEnrollment(subject, dto.year, teacher)
            profEnrolRepo.save(enrollment)
        }
    }

    fun teacherDTOToEntity(dto: TeacherDTO): Teacher {
        return Teacher(
            teachingSince = LocalDate.now(),
            teacherType = dto.teacherType,
            user = User(dto.firstName, dto.lastName, dto.email, paEc.encode(dto.password), true, RoleEnum.TEACHER)
        )
    }
}


