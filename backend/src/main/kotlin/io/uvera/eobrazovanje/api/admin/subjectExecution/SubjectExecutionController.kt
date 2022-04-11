package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
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
@Tag(description = "Endpoints for subject executions.", name = "subject execution")
//endregion
@RequestMapping("/api/admin/subject-execution")
@RestController
class SubjectExecutionController(protected val service: SubjectExecutionService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createSubjectExecution(
        @Validated @RequestBody subjectExecutionDTO: SubjectExecutionCreateDTO
    ): ResponseEntity<SubjectExecutionViewDTO> =
        service.createSubjectExecution(subjectExecutionDTO).ok
}
