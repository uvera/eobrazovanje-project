package io.uvera.eobrazovanje.api.admin.student.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Student
import java.util.*

@EntityView(Student::class)
interface StudentViewDTO {
    val id: UUID
    val firstName: String
    val lastName: String
    val transcriptNumber: String
    val email: String
}