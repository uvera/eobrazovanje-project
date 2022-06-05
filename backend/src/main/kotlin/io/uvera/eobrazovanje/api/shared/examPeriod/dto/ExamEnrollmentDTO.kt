package io.uvera.eobrazovanje.api.shared.examPeriod.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.subjectExecution.dto.SubjectExecutionTableViewDTO
import io.uvera.eobrazovanje.common.repository.ExamPeriod
import java.time.LocalDate
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
