package io.uvera.eobrazovanje.api.admin.teacher.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Teacher
import io.uvera.eobrazovanje.common.repository.TeacherType
import io.uvera.eobrazovanje.common.repository.User

@EntityView(Teacher::class)
interface TeacherResponseDTO {

    val teacherType: TeacherType

    var user: UserDTO

    @EntityView(User::class)
    interface UserDTO {
        var lastName: String
        var email: String
        var firstName: String
    }
}
