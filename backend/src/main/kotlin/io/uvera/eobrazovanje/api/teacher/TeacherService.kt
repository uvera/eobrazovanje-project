package io.uvera.eobrazovanje.api.teacher

import io.uvera.eobrazovanje.api.teacher.dto.TeacherDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.TeacherRepository
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.error.exception.EntityNotFoundException
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


@Service
class TeacherService (protected val repo: TeacherRepository){
    val logger by loggerDelegate()

    fun getTeacher(id: UUID): Optional<Teacher> {
        logger.info("Getting teacher $id")
        return repo.findById(id)
    }

    fun getTeachersByPage(page: Int, records: Int): Any {
        val dbPage: Pageable = PageRequest.of(page, records)
        return repo.findAll(dbPage)
    }

    fun createTeacher(dto: TeacherDTO) : Any {
        val teacher = teacherDTOToEntity(dto)
        print("Creating teacher")
        return repo.save(teacher)
    }

    fun updateTeacher(id: UUID, dto: TeacherDTO): Teacher {
        logger.info("Getting teacher $dto")
        val teacher = repo.findByIdOrNull(id) ?: throw EntityNotFoundException("Teacher by id not found")

        teacher.teacherType = dto.teacherType
        teacher.user = User(dto.firstName, dto.lastName, dto.email, dto.password, true)

        return repo.save(teacher)
    }

    fun deleteTeacher(id: UUID) {
        // TODO: Izvrsi migraciju za isActive
    }
}

fun teacherDTOToEntity(dto: TeacherDTO): Teacher {
    return Teacher(
        teachingSince = LocalDate.now(),
        teacherType = dto.teacherType,
        user = User(dto.firstName, dto.lastName, dto.email, dto.password, true)
    )
}