package io.uvera.eobrazovanje.api.admin.subject.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.Subject
import java.util.*

@EntityView(Subject::class)
interface SubjectViewDTO {
    var id: UUID
    var espb: Int
    var name: String
    var year: Int
}