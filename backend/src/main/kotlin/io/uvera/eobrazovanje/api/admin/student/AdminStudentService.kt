package io.uvera.eobrazovanje.api.admin.student

import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import io.uvera.eobrazovanje.api.admin.student.dto.CreatedStudentDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentUpdateDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.common.service.DigitGenerationService
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.saveAll
import io.uvera.eobrazovanje.util.extensions.update
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class AdminStudentService(
    protected val repo: StudentRepository,
    protected val userRepository: UserRepository,
    protected val digitGenerationService: DigitGenerationService,
) {

    @Transactional
    fun getStudent(id: UUID): StudentViewDTO =
        repo.findByIdAsDto(id) ?: notFoundById<Student>(id)

    @Transactional
    fun getStudentsByPage(page: Int, records: Int): Page<StudentViewDTO> = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    @Transactional
    fun createStudents(students: AdminCreateStudentsDTO): List<CreatedStudentDTO> = repo {
        students.convertToStudents().let { list ->
            list.saveAll().map {
                CreatedStudentDTO(
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    transcriptNumber = it.transcriptNumber,
                    email = it.user.email,
                )
            }
        }
    }

    fun AdminCreateStudentsDTO.convertToStudents() = this.data.map {
        User(
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            password = "{noop}" + digitGenerationService.getRandomPassword(10),
            role = RoleEnum.STUDENT,
        ).let { user ->
            Student(
                transcriptNumber = it.transcriptNumber,
                identificationNumber = it.identificationNumber,
                currentYear = it.currentYear,
                changedPassword = false,
                balance = BigDecimal.ZERO,
                user = user
            )
        }
    }

    fun deleteStudent(id: UUID) = repo {
        if (!existsById(id)) notFoundById<Student>(id)
        return@repo deleteById(id)
    }

    @Transactional(propagation = Propagation.NEVER)
    fun updateStudent(studentId: UUID, studentDTO: StudentUpdateDTO): Any = repo {
        val student = findByIdOrNull(studentId) ?: notFoundById<Student>(studentId)
        student.update {
            transcriptNumber = studentDTO.transcriptNumber
            identificationNumber = studentDTO.identificationNumber
        }
        userRepository {
            student.user.update {
                firstName = studentDTO.firstName
                lastName = studentDTO.lastName
            }
        }
        return@repo getStudent(studentId)
    }

    fun getAllStudents(): Any = repo {
        return@repo findAllStdents()
    }
}
