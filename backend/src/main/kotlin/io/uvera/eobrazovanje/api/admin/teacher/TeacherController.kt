package io.uvera.eobrazovanje.api.admin.teacher

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherSubjectExecutionDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.loggerDelegate
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
    ): Any {
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
    ): Any {
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

    // TEST NEEDED
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subjects")
    fun addTeacherToSubjectExecutions(
        @RequestBody @Validated dto: TeacherSubjectExecutionDTO,
    ): Any {
        return service.addTeacherToSubject(dto).ok
    }
}
