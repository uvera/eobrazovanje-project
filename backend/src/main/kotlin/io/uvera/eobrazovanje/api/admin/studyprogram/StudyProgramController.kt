package io.uvera.eobrazovanje.api.admin.studyprogram

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

//region SwaggerDoc
@Tag(description = "Endpoints for study programs.", name = "study program")
//endregion
@RequestMapping("/api/admin/study-program")
@RestController
class StudyProgramController(protected val service: StudyProgramService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createStudyProgram(@Validated @RequestBody studyProgram: StudyProgramCreateDTO): ResponseEntity<StudyProgramViewDTO> =
        service.createStudyProgram(studyProgram).ok

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getStudyProgram(@PathVariable("id") studyProgramId: UUID): ResponseEntity<StudyProgramViewDTO> =
        service.getStudyProgram(studyProgramId).ok

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateStudyProgram(
        @PathVariable("id") studyProgramId: UUID,
        @Validated @RequestBody studyProgram: StudyProgramCreateDTO
    ): ResponseEntity<StudyProgramViewDTO> =
        service.updateStudyProgram(studyProgramId, studyProgram).ok

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteStudyProgram(
        @PathVariable("id") studyProgramId: UUID
    ): AnyResponseEntity = service.deleteStudyProgram(studyProgramId).let { emptyOk }
}
