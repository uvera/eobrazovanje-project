package io.uvera.eobrazovanje.api.admin.subjectExecution.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalTime
import java.util.UUID

@EntityView(SubjectExecution::class)
interface SubjectExecutionTableViewDTO {
    var id: UUID
    var place: String
    var time: LocalTime
    var weekDay: String
}
