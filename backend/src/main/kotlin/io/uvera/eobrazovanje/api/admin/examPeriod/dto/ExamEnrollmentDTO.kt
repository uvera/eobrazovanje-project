package io.uvera.eobrazovanje.api.admin.examPeriod.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionTableViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.ExamPeriod
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@EntityView(ExamPeriod::class)
interface ExamEnrollmentDTO {
    var id: UUID
    var subjectExecution: SubjectExecutionTableViewDTO
    var examPeriod: ExamPeriodViewDTO

    @EntityView(ExamPeriod::class)
    interface ExamPeriodViewDTO {
        var id: UUID
        var startDate: LocalDate
        var endDate: LocalDate
    }
}