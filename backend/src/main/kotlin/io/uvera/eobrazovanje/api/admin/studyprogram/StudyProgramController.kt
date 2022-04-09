package io.uvera.eobrazovanje.api.admin.studyprogram

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.websocket.server.PathParam

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
}
