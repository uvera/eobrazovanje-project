package io.uvera.eobrazovanje.api.admin.teacher.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalDateTime
import java.util.*

@EntityView(Teacher::class)
interface TeacherEnrollmentViewDTO {
    val id: UUID
    val year: Int
    @get:CollectionMapping
    val subjectExecution: List<SubjectExecutionViewDTO>

    @EntityView(SubjectExecution::class)
    interface SubjectExecutionViewDTO {
        val place: String
        val time: LocalDateTime
        val subject: SubjectViewDTO
    }
}
