package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.common.repository.PreExamActivity
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import io.uvera.eobrazovanje.common.repository.SubjectProfessorEnrollment
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@EntityView(SubjectExecution::class)
interface SubjectExecutionViewDTO {
    var id: UUID
    var place: String
    var time: LocalTime
    var weekDay: String
    @get:CollectionMapping
    val preExamActivities: List<PreExamActivityViewDTO>
    @get:CollectionMapping
    val subjectProfessorEnrollments: List<SubjectProfessorEnrollmentDTO>
    val subject: SubjectViewDTO

    @EntityView(SubjectProfessorEnrollment::class)
    interface SubjectProfessorEnrollmentDTO {
        var teacher: TeacherResponseDTO
    }

    @EntityView(PreExamActivity::class)
    interface PreExamActivityViewDTO {
        var id: UUID
        var name: String
        var points: Int
    }
}
