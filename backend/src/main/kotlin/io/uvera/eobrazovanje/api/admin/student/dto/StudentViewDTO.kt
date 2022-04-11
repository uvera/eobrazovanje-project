package io.uvera.eobrazovanje.api.admin.student.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.User
import java.util.*

@EntityView(Student::class)
interface StudentViewDTO {
    val id: UUID
    val transcriptNumber: String
    val user: StudentUserViewDTO

    @EntityView(User::class)
    interface StudentUserViewDTO {
        val email: String
        val firstName: String
        val lastName: String
    }
}
