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

@RequestMapping("/api/student")
@RestController
@PreAuthorize("hasRole('STUDENT')")
class StudentController(
    protected val service: AdminStudentService
    ) {

    @GetMapping("/whoami")
    fun getCurrentlyLoggedInStudentByEmail(
        @RequestParam(value = "email", required = true, defaultValue = "") email: String
    ): ResponseEntity<StudentViewDTO> = service.getStudentByEmail(email).ok

}