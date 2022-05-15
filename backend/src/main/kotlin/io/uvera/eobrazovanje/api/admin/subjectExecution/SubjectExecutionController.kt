package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

//region SwaggerDoc
@Tag(description = "Endpoints for subject executions.", name = "subject execution")
//endregion
@RequestMapping("/api/admin/subject-execution")
@RestController
@PreAuthorize("hasRole('ADMIN')")
class SubjectExecutionController(protected val service: SubjectExecutionService) {

    @PostMapping
    fun createSubjectExecution(
        @Validated @RequestBody subjectExecutionDTO: SubjectExecutionCreateDTO
    ): ResponseEntity<SubjectExecutionViewDTO> =
        service.createSubjectExecution(subjectExecutionDTO).ok

    @GetMapping("/paged")
    fun getAllSubjectExecutions(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ) = service.getAllSubjectExecutionsPaged(page, records).ok
}
