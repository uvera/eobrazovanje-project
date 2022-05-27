package io.uvera.eobrazovanje.api.admin.student

import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import io.uvera.eobrazovanje.api.admin.student.dto.CreatedStudentDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentUpdateDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.api.admin.student.dto.*
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.common.service.DigitGenerationService
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.saveAll
import io.uvera.eobrazovanje.util.extensions.update
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.lang.Integer.parseInt
import java.math.BigDecimal
import java.util.*

@Service
class AdminStudentService(
    protected val repo: StudentRepository,
    protected val userRepository: UserRepository,
    protected val subjectRepository: SubjectRepository,
    protected val digitGenerationService: DigitGenerationService,
    protected val studyRepo: StudyProgramRepository,
    protected val subjEnrolRepo: SubjectEnrollmentRepository,
    protected val paEc: PasswordEncoder
) {

    @Transactional
    fun getStudent(id: UUID): StudentViewDTO =
        repo.findByIdAsDto(id) ?: notFoundById<Student>(id)

    @Transactional
    fun getStudentByEmail(email: String): StudentViewDTO =
        repo.findByUserEmailAsDto(email) ?: notFoundByEmail<Student>(email)

    @Transactional
    fun getStudentsByPage(page: Int, records: Int): Page<StudentViewDTO> = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    @Transactional
    fun createStudents(students: List<AdminCreateStudentDTO>): List<CreatedStudentDTO> = repo {
        convertToStudents(students).let { list ->
            list.saveAll().onEach {
                val executions = studyRepo.findByIdOrNull(it.studyProgram?.id)?.subjectExecutions
                executions?.forEach { execution ->
                    subjEnrolRepo {
                        SubjectEnrollment(
                            year = it.currentYear, //not sure if this will be this but leave it for now
                            subjectExecution = execution,
                            student = it
                        ).save()
                    }
                }
            }.map {
                CreatedStudentDTO(
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    transcriptNumber = it.transcriptNumber,
                    email = it.user.email,
                )
            }
        }
    }

    fun convertToStudents(students: List<AdminCreateStudentDTO>) = students.map {
        User(
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            password = paEc.encode(it.identificationNumber),

            role = RoleEnum.STUDENT,
        ).let { user ->
            Student(
                transcriptNumber = it.transcriptNumber,
                identificationNumber = it.identificationNumber,
                currentYear = parseInt(it.currentYear),
                changedPassword = false,
                balance = BigDecimal.ZERO,
                user = user,
                studyProgram = studyRepo.findByCodeName(it.studyProgramCode) ?: notFoundByCodeName<StudyProgram>(it.studyProgramCode)
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
        return@repo findAllStudents()
    }

    fun getStudentsWithoutStudyPrograms(): Any = repo {
        return@repo findAllWhereNoStudyProgram()
    }

    fun getStudentSubjects(page: Int, records: Int, studentId: UUID): Page<EnrollmentViewDTO> {
        val req = PageRequest.of(page - 1, records)
        val enrolments = subjEnrolRepo {
            findAllByStudentId(req, studentId)
        }
        return enrolments
    }
}
