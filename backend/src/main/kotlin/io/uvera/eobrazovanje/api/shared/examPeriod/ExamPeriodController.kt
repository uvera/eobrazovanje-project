package io.uvera.eobrazovanje.api.shared.examPeriod

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.shared.examPeriod.dto.ExamEnrollmentDTO
import io.uvera.eobrazovanje.api.shared.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.shared.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.api.shared.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for exam periods.", name = "exam period")
//endregion
@RequestMapping("/api/admin/exam-period")
@RestController
class ExamPeriodController(protected val service: ExamPeriodService) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createExamPeriod(
        @Validated @RequestBody examPeriodDTO: ExamPeriodCreateDTO
    ): ResponseEntity<ExamPeriodViewDTO> =
        service.createExamPeriod(examPeriodDTO).ok

    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    fun getExamPeriodsPaged(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): ResponseEntity<Page<ExamPeriodViewDTO>> {
        return service.getExamPeriodsPaged(page, records).ok
    }

    @PostMapping("/{id}/{subjectExecutionID}/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    fun enrollStudentIntoExamPeriod(
        @PathVariable("id") examPeriodID: UUID,
        @PathVariable("subjectExecutionID") subjectExecutionID: UUID,
    ): AnyResponseEntity {
        val principal by principalDelegate()
        service.enrollStudentToExamPeriod(examPeriodID, principal, subjectExecutionID)
        return emptyOk
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    fun getAllExamPeriods() = service.getExamPeriods().ok

    @GetMapping("/exam-enrollments")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAllStudentExamEnrollments(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): ResponseEntity<Page<ExamEnrollmentDTO>> {
        val principal by principalDelegate()
        return service.getAllStudentExamEnrollments(page, records, principal).ok
    }

    @GetMapping("/{id}/enrollments")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableStudentExamEnrollments(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @PathVariable("id") examPeriodID: UUID,
    ): ResponseEntity<Page<SubjectExecutionViewDTO>> {
        val principal by principalDelegate()
        return service.getStudentAvailableEnrollments(page, records, examPeriodID, principal).ok
    }

    @GetMapping("/{id}/professor-exams")
    @PreAuthorize("hasRole('TEACHER')")
    fun getAvailableProfessorExamPeriodExecutions(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @PathVariable("id") examPeriodID: UUID,
    ): ResponseEntity<Page<SubjectExecutionViewDTO>> {
        val principal by principalDelegate()
        return service.getProfessorAvailableExamPeriodExecutions(page, records, examPeriodID, principal).ok
    }
}
