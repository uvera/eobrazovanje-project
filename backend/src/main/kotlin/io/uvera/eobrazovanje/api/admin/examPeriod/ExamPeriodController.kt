package io.uvera.eobrazovanje.api.admin.examPeriod

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//region SwaggerDoc
@Tag(description = "Endpoints for exam periods.", name = "exam period")
//endregion
@RequestMapping("/api/admin/exam-period")
@RestController
class ExamPeriodController(protected val service: ExamPeriodService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createExamPeriod(
        @Validated @RequestBody examPeriodDTO: ExamPeriodCreateDTO
    ): ResponseEntity<ExamPeriodViewDTO> =
        service.createExamPeriod(examPeriodDTO).ok
}