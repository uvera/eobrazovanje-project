package io.uvera.eobrazovanje.api.admin.announcement

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.announcement.dto.AnnouncementCreateDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

//region SwaggerDoc
@Tag(description = "Endpoints for announcements.", name = "announcement")
//endregion
@RequestMapping("/api/admin/announcement")
@RestController
class AnnouncementController(
    protected val service: AnnouncementService
) {
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    fun getAnnouncementsBySubjectExecution(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @PathVariable("id") subjectExecutionId: UUID
    ) = service.getAnnouncementsByPrincipalAndSubjectExecution(page, records, subjectExecutionId).ok

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    fun createAnnouncement(
        @Validated @RequestBody announcement: AnnouncementCreateDTO
    ) = service.createAnnouncement(announcement).ok
}