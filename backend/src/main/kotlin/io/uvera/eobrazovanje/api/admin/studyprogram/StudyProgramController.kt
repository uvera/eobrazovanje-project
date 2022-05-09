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
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for study programs.", name = "study program")
//endregion
@RequestMapping("/api/admin/study-program")
@RestController
@PreAuthorize("hasRole('ADMIN')")
class StudyProgramController(protected val service: StudyProgramService) {

    @GetMapping("/{id}")
    fun getStudyProgram(@PathVariable("id") studyProgramId: UUID): ResponseEntity<StudyProgramViewDTO> =
        service.getStudyProgram(studyProgramId).ok

    @PostMapping
    fun createStudyProgram(@Validated @RequestBody studyProgram: StudyProgramCreateDTO): ResponseEntity<StudyProgramViewDTO> =
        service.createStudyProgram(studyProgram).ok

    @PutMapping("/{id}")
    fun updateStudyProgram(
        @PathVariable("id") studyProgramId: UUID,
        @Validated @RequestBody studyProgram: StudyProgramCreateDTO
    ): ResponseEntity<StudyProgramViewDTO> =
        service.updateStudyProgram(studyProgramId, studyProgram).ok

    @DeleteMapping("/{id}")
    fun deleteStudyProgram(
        @PathVariable("id") studyProgramId: UUID
    ): AnyResponseEntity = service.deleteStudyProgram(studyProgramId).let { emptyOk }

    @GetMapping("/paged")
    fun getAllStudyPrograms(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ) = service.getAllStudyProgramsPaged(page, records).ok
}
