package io.uvera.eobrazovanje.api.admin.student

import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/student")
@RestController
@PreAuthorize("hasRole('STUDENT')")
class StudentController(
    protected val service: AdminStudentService,
) {

    @GetMapping("/whoami")
    fun getCurrentlyLoggedInStudentByEmail(
        @RequestParam(value = "email", required = false) email: String?,
    ): ResponseEntity<StudentViewDTO> {
        return if (email != null) service.getStudentByEmail(email).ok
        else service.getStudentFromPrincipal().ok
    }

    @GetMapping("/subjects")
    fun getStudentSubjects(
        @RequestParam(value = "page", required = true, defaultValue = "1") page: Int,
        @RequestParam(value = "records", required = true, defaultValue = "10") records: Int,
        @RequestParam(value = "id", required = true, defaultValue = "") id: UUID,
    ): Any = service.getStudentSubjects(page, records, id).ok
}
