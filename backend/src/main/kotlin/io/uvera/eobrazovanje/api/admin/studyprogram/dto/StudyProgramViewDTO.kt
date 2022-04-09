package io.uvera.eobrazovanje.api.admin.studyprogram.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.StudyProgram
import io.uvera.eobrazovanje.common.repository.Subject
import java.util.*

@EntityView(StudyProgram::class)
interface StudyProgramViewDTO {
    var id: UUID
    var name: String
    var codeName: String
    @get:CollectionMapping
    val subjects: List<SubjectViewDTO>

    @EntityView(Subject::class)
    interface SubjectViewDTO {
        var id: UUID
        var espb: Int
        var name: String
        var year: Int
    }
}
