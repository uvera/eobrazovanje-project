package io.uvera.eobrazovanje.api.shared.teacher.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.common.repository.SubjectEnrollment
import java.util.*

@EntityView(SubjectEnrollment::class)
interface TeacherStudentViewDTO {
    val id: UUID
    val year: Int
    val student: StudentViewDTO
}
