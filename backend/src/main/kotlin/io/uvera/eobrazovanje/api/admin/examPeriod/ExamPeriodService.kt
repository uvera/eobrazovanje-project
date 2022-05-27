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
import org.springframework.data.domain.Pageable
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
    protected val subjectEnRepo: SubjectEnrollmentRepository
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
        val studentEnrollments = enrollmentRepo.findByExamPeriodForStudent(examPeriodID)
        val student = studentRepo.findByUserEmailOrNull(principal.email) ?: notFoundByEmail<Student>(principal.email)
        val studentSubjects = subjectEnRepo.findAllByStudentId(student.id)
        val req = PageRequest.of(page - 1, records)
        val executions = studentSubjects.map { it.subjectExecution }
        return if (studentEnrollments.isEmpty()) {
            subjectExRepo.findAllWithIds(req, executions.map { it.id })
        } else {
            val newAvailableList = mutableListOf<SubjectExecution>()
            executions.forEach { subjectEn ->
                studentEnrollments.forEach { enrol ->
                    if (enrol.subjectExecution.id != subjectEn.id) {
                        newAvailableList.add(subjectEn)
                    }
                }
            }
            subjectExRepo.findAllWithIds(req, newAvailableList.map { it.id })
        }
    }

    @Transactional
    fun enrollStudentToExamPeriod(examPeriodID: UUID, principal: CustomUserDetails, subjectExecutionID: UUID): String {
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
            "success"
        } else {
            "Insufficient funds"
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
