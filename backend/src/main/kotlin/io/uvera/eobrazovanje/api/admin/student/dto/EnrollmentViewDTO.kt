package io.uvera.eobrazovanje.api.admin.student.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.User
import java.time.LocalDateTime
import java.util.*

@EntityView(Student::class)
interface EnrollmentViewDTO {
    val id: UUID
    val year: Int
    val subjectExecution: SubjectExecutionViewDTO

    @EntityView(User::class)
    interface SubjectExecutionViewDTO {
        val place: String
        val time: LocalDateTime
        val subject: SubjectViewDTO
    }
}