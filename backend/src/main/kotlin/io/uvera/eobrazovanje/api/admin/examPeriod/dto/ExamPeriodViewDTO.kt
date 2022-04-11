package io.uvera.eobrazovanje.api.admin.examPeriod.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.ExamPeriod
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@EntityView(ExamPeriod::class)
interface ExamPeriodViewDTO {
    var id: UUID
    var name: String
    var startDate: LocalDate
    var endDate: LocalDate
    @get:CollectionMapping
    val subjectExecutions: List<SubjectExecutionViewDTO>

    @EntityView(SubjectExecution::class)
    interface SubjectExecutionViewDTO {
        var id: UUID
        var place: String
        var time: LocalDateTime
    }
}