package io.uvera.eobrazovanje.api.shared.student

import io.uvera.eobrazovanje.api.shared.student.dto.*
import io.uvera.eobrazovanje.common.repository.Student
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

@RequestMapping("/api/admin/student")
@RestController
@PreAuthorize("hasRole('ADMIN')")
class AdminStudentController(protected val service: AdminStudentService) {
    val logger by loggerDelegate()

    @PostMapping
    fun createStudents(@Validated @RequestBody students: List<AdminCreateStudentDTO>): ResponseEntity<List<CreatedStudentDTO>> =
        service.createStudents(students).ok

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable("id") id: UUID): ResponseEntity<StudentViewDTO> = service.getStudent(id).ok

    @GetMapping("/paged")
    fun getStudentsByPage(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
    ): ResponseEntity<Page<StudentViewDTO>> {
        logger.info("Pagination ${Student::class.simpleName} with params: { page: $page, records: $records }")
        return service.getStudentsByPage(page, records).ok
    }

    @PutMapping("/{id}")
    fun updateStudent(
        @PathVariable("id") studentId: UUID,
        @Validated @RequestBody studentDTO: StudentUpdateDTO,
    ) = service.updateStudent(studentId, studentDTO).ok

    @DeleteMapping("/{id}")
    fun deleteStudent(
        @PathVariable("id") studentId: UUID,
    ): AnyResponseEntity = service.deleteStudent(studentId).let { emptyOk }

    @GetMapping("/all")
    fun getAllStudents() = service.getAllStudents().ok

    @GetMapping("/no-study-programs")
    fun getStudentsWithoutStudyPrograms() = service.getStudentsWithoutStudyPrograms().ok
}
