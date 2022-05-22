package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionUpdateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @GetMapping("/{id}")
    fun getSubjectExecution(@PathVariable("id") executionID: UUID) = service.getSubjectExecution(executionID).ok

    @PutMapping("/{id}")
    fun updateSubjectExecution(
        @PathVariable("id") id: UUID,
        @RequestBody @Validated dto: SubjectExecutionUpdateDTO,
    ): Any {
        return service.updateSubjectExecution(id, dto).ok
    }

    @GetMapping("/all")
    fun getAllSubjectExecutions() = service.getAllSubjectExecutions().ok
}
