package io.uvera.eobrazovanje.api.admin.examPeriod

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

//region SwaggerDoc
@Tag(description = "Endpoints for exam periods.", name = "exam period")
//endregion
@RequestMapping("/api/admin/exam-period")
@RestController
@PreAuthorize("hasRole('ADMIN')")
class ExamPeriodController(protected val service: ExamPeriodService) {

    @PostMapping
    fun createExamPeriod(
        @Validated @RequestBody examPeriodDTO: ExamPeriodCreateDTO
    ): ResponseEntity<ExamPeriodViewDTO> =
        service.createExamPeriod(examPeriodDTO).ok

    @GetMapping("/paged")
    fun getExamPeriodsPaged(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): ResponseEntity<Page<ExamPeriodViewDTO>> {
        return service.getExamPeriodsPaged(page, records).ok
    }

}
