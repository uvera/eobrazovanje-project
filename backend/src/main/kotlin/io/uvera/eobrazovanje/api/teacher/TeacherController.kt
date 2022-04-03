package io.uvera.eobrazovanje.api.teacher

import io.swagger.v3.oas.annotations.tags.Tag
import io.uvera.eobrazovanje.api.teacher.dto.TeacherDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

//region SwaggerDoc
@Tag(description = "Endpoints for teachers.", name = "teacher")
//endregion
@RestController
@RequestMapping("/api/teacher")
class TeacherController (protected val service: TeacherService) {

    @PreAuthorize("hasRole('ADMIN, STUDENT')")
    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable("id") id: UUID): Any {
        return service.getTeacher(id).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    fun getTeachersByPage(@RequestParam(value = "page", required = true, defaultValue = "0") page: Int,
                          @RequestParam(value = "records", required = true, defaultValue = "10") records: Int): Any {
        return service.getTeachersByPage(page, records).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    fun createTeacher(@RequestBody @Validated dto: TeacherDTO): Any {
        return service.createTeacher(dto).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateTeacher(@PathVariable("id") id: UUID,
                      @RequestBody @Validated dto: TeacherDTO): Any {
        return service.updateTeacher(id, dto).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteTeacher(@PathVariable("id") id: UUID): Any {
        return service.deleteTeacher(id).ok
    }
}