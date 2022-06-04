package io.uvera.eobrazovanje.api.admin.heldExam

import io.uvera.eobrazovanje.api.admin.heldExam.dto.CreateHeldExamDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.CreateHeldExamResultsDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.HeldExamResultViewDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.HeldExamViewDTO
import io.uvera.eobrazovanje.api.admin.heldExam.dto.StudentEnrollmentViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.data.domain.PageRequest
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
    protected val studentRepo: StudentRepository,
    protected val examEnrollmentRepository: ExamEnrollmentRepository,
    protected val resultRepo: PreExamActivityResultRepository,
    protected val heldExamResultRepo: HeldExamResultRepository
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

    @Transactional
    fun createHeldExamResults(results: List<CreateHeldExamResultsDTO>) {
        results.forEach {
            heldExamResultRepo.save(
                HeldExamResult(
                    score = it.score,
                    heldExam = repo.findByIdOrNull(it.heldExamId) ?: notFoundById<HeldExam>(it.heldExamId),
                    student = studentRepo.findByIdOrNull(it.studentId) ?: notFoundById<Student>(it.studentId)
                )
            )
        }
    }

    fun getEnrolledStudents(examPeriodID: UUID, subjExID: UUID): Any {
        val studentEnrollments = examEnrollmentRepository.findByExamPeriodAndSubjectExecution(examPeriodID, subjExID)
        val returnList = mutableListOf<StudentEnrollmentViewDTO>()
        // need to check if held exam entity is created before accessing students for marking grades, should break if not
        val heldExam = repo.findByExamPeriodAndSubjectExecution(examPeriodID, subjExID) ?: notFoundById<HeldExam>(examPeriodID)
        studentEnrollments.forEach { enrollment ->
            val studentResults = resultRepo.findAllByStudentAndSubjEx(enrollment.student.id, subjExID)
            var preExamActivitiesScore = 0
            studentResults.forEach { result -> preExamActivitiesScore += result.score }
            returnList.add(
                StudentEnrollmentViewDTO(
                    studentId = enrollment.student.id,
                    name = enrollment.student.user.firstName + ' ' + enrollment.student.user.lastName,
                    transcriptNumber = enrollment.student.transcriptNumber,
                    preExamActivitiesSum = preExamActivitiesScore
                )
            )
        }
        return returnList
    }

    fun getStudentExamResults(page: Int, records: Int, examPeriodID: UUID, studentId: UUID): Any {
        val req = PageRequest.of(page - 1, records)
        val resultData = heldExamResultRepo.findByStudent(examPeriodID, studentId, req) val returnList = mutableListOf<HeldExamResultViewDTO>()
        resultData.forEach { examResult ->
            val studentPreExamActivities = resultRepo.findAllByStudentAndSubjEx(studentId, examResult.heldExam.subjectExecution.id)
            var preExamActivitiesScore = 0
            studentPreExamActivities.forEach { result -> preExamActivitiesScore += result.score }
            returnList.add(
                HeldExamResultViewDTO(
                    subject = examResult.heldExam.subjectExecution.subject.name,
                    date = examResult.heldExam.date,
                    score = preExamActivitiesScore + examResult.score)
            )
        }

        return returnList
    }
}
