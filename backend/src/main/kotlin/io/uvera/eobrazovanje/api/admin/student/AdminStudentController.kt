package io.uvera.eobrazovanje.api.admin.student

import io.uvera.eobrazovanje.api.admin.student.dto.AdminCreateStudentsDTO
import io.uvera.eobrazovanje.api.admin.student.dto.CreatedStudentDTO
import io.uvera.eobrazovanje.util.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/admin/student")
@RestController
class AdminStudentController(protected val service: AdminStudentService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createStudents(@Validated @RequestBody students: AdminCreateStudentsDTO): ResponseEntity<List<CreatedStudentDTO>> =
        service.createStudents(students).ok
}
