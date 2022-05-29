package io.uvera.eobrazovanje.api.admin.heldExam

import io.uvera.eobrazovanje.api.admin.examPeriod.ExamPeriodService
import io.uvera.eobrazovanje.api.admin.heldExam.dto.CreateHeldExamDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.HeldExamViewDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.StudentEnrollmentViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.SubjectExecutionService
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class HeldExamService(
    protected val repo: HeldExamRepository,
    protected val examPeriodRepository: ExamPeriodRepository,
    protected val subjectExecutionRepository: SubjectExecutionRepository,
    protected val teacherRepository: TeacherRepository,
    protected val examEnrollmentRepository: ExamEnrollmentRepository,
    protected val resultRepo: PreExamActivityResultRepository
) {
    fun getHeldExamById(examPeriodID: UUID, subjExID: UUID): HeldExamViewDTO {
        return repo.findByExamPeriodAndSubjectExecution(examPeriodID, subjExID) ?: notFoundById<HeldExam>(examPeriodID)
    }

    @Transactional
    fun createHeldExam(heldExamDTO: CreateHeldExamDTO) {
        val principal by principalDelegate()
        val examPeriod = examPeriodRepository.findByExamPeriodID(heldExamDTO.examPeriodId) ?: notFoundById<ExamPeriod>(heldExamDTO.examPeriodId)
        val subjEx = subjectExecutionRepository.findByIdOrNull(heldExamDTO.subjectExecutionId) ?: notFoundById<SubjectExecution>(heldExamDTO.subjectExecutionId)
        val teacher = teacherRepository.findByUserEmail(principal.email) ?: notFoundByEmail<Teacher>(principal.email)
        repo.save(HeldExam(heldExamDTO.date, examPeriod, subjEx, teacher = teacher))
    }

    fun getEnrolledStudents(examPeriodID: UUID, subjExID: UUID): MutableList<StudentEnrollmentViewDTO> {
        val studentEnrollments = examEnrollmentRepository.findByExamPeriodAndSubjectExecution(examPeriodID, subjExID)
        val returnList = mutableListOf<StudentEnrollmentViewDTO>()
        // need to check if held exam entity is created before accessing students for marking grades, should break if not
        val heldExam = repo.findByExamPeriodAndSubjectExecution(examPeriodID, subjExID) ?: notFoundById<HeldExam>(examPeriodID)
        // TODO is list empty or not? HeldExamResults
        studentEnrollments.forEach { enrollment ->
            returnList.add(StudentEnrollmentViewDTO(
                studentId = enrollment.student.id,
                preExamActivitiesSum = resultRepo.findAllByStudentAndSubjEx(enrollment.student.id, subjExID).sumOf { it.points },
                studentTranscriptNumber = enrollment.student.transcriptNumber
            ))
        }
        return returnList
    }
}