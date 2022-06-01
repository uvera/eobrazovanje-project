package io.uvera.eobrazovanje.api.admin.heldExam

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.examPeriod.ExamPeriodService
import io.uvera.eobrazovanje.api.admin.heldExam.dto.CreateHeldExamDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

//region SwaggerDoc
@Tag(description = "Endpoints for manipulating exam results.", name = "held exams")
//endregion
@RequestMapping("/api/held-exam")
@RestController
class HeldExamController(
    protected val service: HeldExamService
) {
    @GetMapping("/{examPeriodID}/{subjExId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    fun getHeldExamById(
        @PathVariable("examPeriodID") examPeriodID: UUID,
        @PathVariable("subjExId") subjExId: UUID
    ) = service.getHeldExamById(examPeriodID, subjExId).ok

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    fun createHeldExam(
        @Validated @RequestBody heldExam: CreateHeldExamDTO
    ) = service.createHeldExam(heldExam).ok

    @GetMapping("/{examPeriodID}/{subjExId}/enrolled-students")
    @PreAuthorize("hasRole('TEACHER')")
    fun getStudentsEnrolledInExam(
        @PathVariable("examPeriodID") examPeriodID: UUID,
        @PathVariable("subjExId") subjExId: UUID,
    ) = service.getEnrolledStudents(examPeriodID, subjExId).ok
}