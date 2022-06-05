package io.uvera.eobrazovanje.api.shared.preExamActivity

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.shared.preExamActivity.dto.PreExamActivityCreateDTO
import io.uvera.eobrazovanje.api.shared.preExamActivity.dto.PreExamActivityResultCreateDTO
import io.uvera.eobrazovanje.api.shared.preExamActivity.dto.PreExamActivityViewDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for pre exam activities.", name = "pre exam activity")
//endregion
@RequestMapping("/api/admin/pre-exam-activity")
@RestController
class PreExamActivityController(protected val service: PreExamActivityService) {

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPreExamActivity(@PathVariable("id") preExamActivityID: UUID): ResponseEntity<PreExamActivityViewDTO> =
        service.getPreExamActivity(preExamActivityID).ok

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createPreExamActivity(@Validated @RequestBody preExamActivities: PreExamActivityCreateDTO): ResponseEntity<PreExamActivityViewDTO> =
        service.createPreExamActivity(preExamActivities).ok

    @PostMapping("/add-results")
    @PreAuthorize("hasRole('TEACHER')")
    fun createPreExamActivityResult(@Validated @RequestBody preExamResults: List<PreExamActivityResultCreateDTO>) =
        service.createResults(preExamResults).ok

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateStudyProgram(
        @PathVariable("id") preExamActivityID: UUID,
        @Validated @RequestBody preExamActivity: PreExamActivityCreateDTO,
    ): ResponseEntity<PreExamActivityViewDTO> =
        service.updatePreExamActivity(preExamActivityID, preExamActivity).ok

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deletePreExamActivity(
        @PathVariable("id") preExamActivityID: UUID,
    ): AnyResponseEntity = service.deletePreExamActivity(preExamActivityID).let { emptyOk }

    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllPreExamActivities(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ) = service.getAllPreExamActivitiesPaged(page, records).ok

    @GetMapping("/{subjectExecutionId}/{studentId}/student")
    @PreAuthorize("hasRole('TEACHER')")
    fun getStudentPreExamActivitiesBySubject(
        @PathVariable("studentId") studentId: UUID,
        @PathVariable("subjectExecutionId") subjectExecutionId: UUID,
    ) = service.getStudentPreExamActivities(studentId, subjectExecutionId).ok

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun findAllPreExamActivities() = service.getAllPreExamActivities().ok
}
