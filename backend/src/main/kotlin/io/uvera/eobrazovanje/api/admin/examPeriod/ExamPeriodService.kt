package io.uvera.eobrazovanje.api.admin.examPeriod

import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamEnrollmentDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.security.configuration.CustomUserDetails
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class ExamPeriodService(
    protected val repo: ExamPeriodRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
    protected val studentRepo: StudentRepository,
    protected val enrollmentRepo: ExamEnrollmentRepository,
    protected val subjectEnRepo: SubjectEnrollmentRepository,
    protected val profRepo: TeacherRepository,
    protected val profEnrollRepo: SubjectProfessorEnrollmentRepository
) {

    @Transactional
    fun createExamPeriod(dto: ExamPeriodCreateDTO): ExamPeriodViewDTO = repo {
        dtoToEntity(dto).save().also { examEntity ->
            subjectExRepo {
                findAllByIds(dto.subjectExecutionIds).updateEach {
                    examPeriods.add(examEntity)
                }
            }
        }.let {
            getExamPeriod(it.id)
        }
    }

    @Transactional(propagation = Propagation.NEVER)
    fun getExamPeriodsPaged(page: Int, records: Int): Page<ExamPeriodViewDTO> = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllPaged(req)
    }

    @Transactional
    fun getExamPeriod(examPeriodID: UUID): ExamPeriodViewDTO =
        repo.findByIdAsDto(examPeriodID) ?: notFoundById<ExamPeriod>(examPeriodID)

    @Transactional
    fun getExamPeriods(): List<ExamPeriodViewDTO> = repo.findAllForStudent()

    @Transactional
    fun getAllStudentExamEnrollments(page: Int, records: Int, principal: CustomUserDetails): Page<ExamEnrollmentDTO> = enrollmentRepo {
        val student = studentRepo.findByUserEmailOrNull(principal.email) ?: notFoundByEmail<Student>(principal.email)
        val req = PageRequest.of(page - 1, records)
        return@enrollmentRepo findByExamPeriodForStudent(req, student.id)
    }

    @Transactional
    fun getStudentAvailableEnrollments(page: Int, records: Int, examPeriodID: UUID, principal: CustomUserDetails): Page<SubjectExecutionViewDTO> {
        val studentEnrollments = enrollmentRepo.findByExamPeriodForStudent(examPeriodID).map { it.subjectExecution.id }
        val student = studentRepo.findByUserEmailOrNull(principal.email) ?: notFoundByEmail<Student>(principal.email)
        val studentSubjects = subjectEnRepo.findAllByStudentId(student.id).map { it.subjectExecution.id }
        val req = PageRequest.of(page - 1, records)
        return if (studentEnrollments.isEmpty()) {
            subjectExRepo.findAllWithIds(req, studentSubjects)
        } else {
            val newAvailableList = mutableListOf<UUID>()
            studentSubjects.forEach { enrol ->
                if (!studentEnrollments.contains(enrol)) {
                    newAvailableList.add(enrol)
                }
            }
            subjectExRepo.findAllWithIds(req, newAvailableList)
        }
    }

    @Transactional
    fun getProfessorAvailableExamPeriodExecutions(page: Int, records: Int, examPeriodID: UUID, principal: CustomUserDetails): Page<SubjectExecutionViewDTO> {
        val examPeriod = repo { findByExamPeriodID(examPeriodID) ?: notFoundById<ExamPeriod>(examPeriodID) }
        val examPeriodSubjEXids = examPeriod.subjectExecutions.map { it.id }
        val professor = profRepo { findByUserEmail(principal.email) ?: notFoundByEmail<Teacher>(principal.email) }
        val professorEnrollmentsSubjExIds = profEnrollRepo { findByProfessorId(professor.id) }.map { it.subjectExecution.id }
        val newList = mutableListOf<UUID>()
        val req = PageRequest.of(page - 1, records)
        professorEnrollmentsSubjExIds.forEach { enrolEx ->
            if (examPeriodSubjEXids.contains(enrolEx)) {
                newList.add(enrolEx)
            }
        }
        return subjectExRepo.findAllWithIds(req, newList)
    }

    @Transactional
    fun enrollStudentToExamPeriod(examPeriodID: UUID, principal: CustomUserDetails, subjectExecutionID: UUID): Boolean {
        val student = studentRepo.findByUserEmailOrNull(principal.email) ?: notFoundByEmail<Student>(principal.email)
        val examPeriod = repo.findByIdOrNull(examPeriodID) ?: notFoundById<ExamPeriod>(examPeriodID)
        val subjectExecution = subjectExRepo.findByIdOrNull(subjectExecutionID) ?: notFoundById<SubjectExecution>(subjectExecutionID)
        return if (student.balance.intValueExact() >= 200) {
            student.balance -= BigDecimal.valueOf(200)
            studentRepo { student.save() }
            enrollmentRepo {
                ExamEnrollment(
                    student = student,
                    subjectExecution = subjectExecution,
                    examPeriod = examPeriod
                ).save()
            }
            true
        } else {
            entityStateError("Insufficient funds")
        }
    }

    fun dtoToEntity(dto: ExamPeriodCreateDTO): ExamPeriod {
        return ExamPeriod(
            name = dto.name,
            endDate = dto.endDate,
            startDate = dto.startDate,
            subjectExecutions = subjectExRepo.findAllByIds(dto.subjectExecutionIds).toMutableSet()
        )
    }
}
