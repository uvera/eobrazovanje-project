package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.PreExamActivity
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalDateTime
import java.util.UUID

@EntityView(SubjectExecution::class)
interface SubjectExecutionViewDTO {
    var place: String
    var time: LocalDateTime
    @get:CollectionMapping
    val preExamActivities: List<PreExamActivityViewDTO>
    val subject: SubjectViewDTO

    @EntityView(PreExamActivity::class)
    interface PreExamActivityViewDTO {
        var id: UUID
        var name: String
        var points: Int
    }
}
