package io.uvera.eobrazovanje.api.admin.subject

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectCreateDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for subjects.", name = "subject")
//endregion
@RequestMapping("/api/admin/subject")
@RestController
class SubjectController(protected val service: SubjectService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createSubject(@Validated @RequestBody subjectDTO: SubjectCreateDTO): ResponseEntity<SubjectViewDTO> =
        service.createSubject(subjectDTO).ok

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getSubject(@PathVariable("id") subjectId: UUID): ResponseEntity<SubjectViewDTO> =
        service.getSubject(subjectId).ok

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable("id") subjectId: UUID,
        @Validated @RequestBody subjectDTO: SubjectCreateDTO
    ): ResponseEntity<SubjectViewDTO> =
        service.updateSubject(subjectId, subjectDTO).ok

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteSubject(
        @PathVariable("id") subjectId: UUID
    ): AnyResponseEntity = service.deleteSubject(subjectId).let { emptyOk }
}
