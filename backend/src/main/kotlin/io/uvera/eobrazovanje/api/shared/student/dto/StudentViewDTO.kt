package io.uvera.eobrazovanje.api.shared.student.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Student
import io.uvera.eobrazovanje.common.repository.User
import java.math.BigDecimal
import java.util.*

@EntityView(Student::class)
interface StudentViewDTO {
    val id: UUID
    val transcriptNumber: String
    val identificationNumber: String
    val balance: BigDecimal
    val user: StudentUserViewDTO

    @EntityView(User::class)
    interface StudentUserViewDTO {
        val email: String
        val firstName: String
        val lastName: String
    }
}
