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
@PreAuthorize("hasRole('ADMIN')")
@RestController
class SubjectController(protected val service: SubjectService) {

    @PostMapping
    fun createSubject(@Validated @RequestBody subjectDTO: SubjectCreateDTO): ResponseEntity<SubjectViewDTO> =
        service.createSubject(subjectDTO).ok

    @GetMapping("/{id}")
    fun getSubject(@PathVariable("id") subjectId: UUID): ResponseEntity<SubjectViewDTO> =
        service.getSubject(subjectId).ok

    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable("id") subjectId: UUID,
        @Validated @RequestBody subjectDTO: SubjectCreateDTO
    ): ResponseEntity<SubjectViewDTO> =
        service.updateSubject(subjectId, subjectDTO).ok

    @DeleteMapping("/{id}")
    fun deleteSubject(
        @PathVariable("id") subjectId: UUID
    ): AnyResponseEntity = service.deleteSubject(subjectId).let { emptyOk }

    @GetMapping
    fun getAllSubjects(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ) = service.getAllSubjectsPaged(page, records).ok

    @GetMapping("/available")
    fun getAllSubjectsWithoutStudyPrograms() = service.getAllSubjectsWithoutStudyPrograms().ok
}
