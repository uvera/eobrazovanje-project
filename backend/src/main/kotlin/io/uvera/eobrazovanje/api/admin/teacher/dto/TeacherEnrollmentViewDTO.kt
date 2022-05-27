package io.uvera.eobrazovanje.api.admin.teacher.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.student.dto.EnrollmentViewDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import io.uvera.eobrazovanje.common.repository.SubjectProfessorEnrollment
import java.time.LocalDateTime
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
