package io.uvera.eobrazovanje.api.shared.teacher.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.student.dto.EnrollmentViewDTO
import io.uvera.eobrazovanje.common.repository.SubjectProfessorEnrollment
import io.uvera.eobrazovanje.common.repository.Teacher
import java.util.*

@EntityView(Teacher::class)
interface TeacherEnrollmentViewDTO {
    val id: UUID
    @get:CollectionMapping
    val subjectProfessorEnrollments: List<SubjectProfessorEnrollmentViewDTO>

    @EntityView(SubjectProfessorEnrollment::class)
    interface SubjectProfessorEnrollmentViewDTO {
        val year: Int
        val subjectExecution: EnrollmentViewDTO.SubjectExecutionViewDTO
    }
}
