package io.uvera.eobrazovanje.api.shared.student.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.User
import java.time.LocalTime
import java.util.*

@EntityView(Student::class)
interface EnrollmentViewDTO {
    val id: UUID
    val year: Int
    val subjectExecution: SubjectExecutionViewDTO

    @EntityView(User::class)
    interface SubjectExecutionViewDTO {
        val id: UUID
        val place: String
        val weekDay: String
        val time: LocalTime
        val subject: SubjectViewDTO
    }
}
