package io.uvera.eobrazovanje.api.admin.student

import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import io.uvera.eobrazovanje.api.admin.student.dto.CreatedStudentDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentUpdateDTO
import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.util.AnyResponseEntity
import io.uvera.eobrazovanje.util.extensions.emptyOk
import io.uvera.eobrazovanje.util.extensions.ok
import io.uvera.eobrazovanje.util.loggerDelegate
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/admin/student")
@RestController
class AdminStudentController(protected val service: AdminStudentService) {
    val logger by loggerDelegate()

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createStudents(@Validated @RequestBody students: AdminCreateStudentsDTO): ResponseEntity<List<CreatedStudentDTO>> =
        service.createStudents(students).ok

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getStudentById(@PathVariable("id") id: UUID): ResponseEntity<StudentViewDTO> = service.getStudent(id).ok

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paged")
    fun getStudentsByPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): Any {
        logger.info("Pagination ${Student::class.simpleName} with params: { page: $page, records: $records }")
        return service.getStudentsByPage(page, records).ok
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateStudent(
        @PathVariable("id") studentId: UUID,
        @Validated @RequestBody studentDTO: StudentUpdateDTO
    ) = service.updateStudent(studentId, studentDTO).ok

    @DeleteMapping("/{id}")
    fun deleteStudent(
        @PathVariable("id") studentId: UUID
    ): AnyResponseEntity = service.deleteStudent(studentId).let { emptyOk }
}
