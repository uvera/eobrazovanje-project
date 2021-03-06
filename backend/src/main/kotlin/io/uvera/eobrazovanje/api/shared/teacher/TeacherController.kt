package io.uvera.eobrazovanje.api.shared.teacher

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.shared.teacher.dto.TeacherDTO
import io.uvera.eobrazovanje.api.shared.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.api.shared.teacher.dto.TeacherSubjectExecutionDTO
import io.uvera.eobrazovanje.api.shared.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for teachers.", name = "teacher")
//endregion
@RestController
@RequestMapping("/api/teacher")
class TeacherController(protected val service: TeacherService) {
    val logger by loggerDelegate()

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable("id") id: UUID): ResponseEntity<TeacherResponseDTO> = service.getTeacher(id).ok

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paged")
    fun getTeachersByPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): ResponseEntity<Page<TeacherResponseDTO>> {
        logger.info("Pagination ${Teacher::class.simpleName} with params: { page: $page, records: $records }")
        return service.getTeachersByPage(page, records).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    fun createTeacher(@RequestBody @Validated dto: TeacherDTO): ResponseEntity<TeacherResponseDTO> =
        service.createTeacher(dto).ok

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateTeacher(
        @PathVariable("id") id: UUID,
        @RequestBody @Validated dto: TeacherUpdateDTO,
    ): ResponseEntity<TeacherResponseDTO> {
        return service.updateTeacher(id, dto).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteTeacher(@PathVariable("id") id: UUID): AnyResponseEntity =
        service.deleteTeacher(id).let {
            emptyOk
        }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    fun getAllTeachers() = service.getAllTeachers().ok

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subjects")
    fun addTeacherToSubjectExecutions(
        @RequestBody @Validated dto: TeacherSubjectExecutionDTO,
    ): ResponseEntity<Unit> = service.addTeacherToSubject(dto).ok

    @GetMapping("/whoami")
    fun getCurrentlyLoggedInTeacherByEmail(
        @RequestParam(value = "email", required = true, defaultValue = "") email: String,
    ): ResponseEntity<TeacherResponseDTO> = service.getTeacherByEmail(email).ok

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/subjects")
    fun getTeacherSubjects(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @RequestParam(value = "id", required = true, defaultValue = "") id: UUID,
    ) = service.getTeacherSubjects(page, records, id).ok

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/subjects-all")
    fun getTeacherSubjects(
        @RequestParam(value = "id", required = true, defaultValue = "") id: UUID,
    ) = service.getTeachersSubjects(id).ok

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/subject-students")
    fun getTeacherStudentsBySubjectExecution(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @RequestParam(value = "id", required = true, defaultValue = "") subjectId: UUID,
    ) = service.getTeacherStudentsBySubject(page, records, subjectId).ok
}
