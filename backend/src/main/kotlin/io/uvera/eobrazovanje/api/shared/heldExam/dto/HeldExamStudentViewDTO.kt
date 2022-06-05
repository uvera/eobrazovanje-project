package io.uvera.eobrazovanje.api.shared.heldExam.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.student.dto.EnrollmentViewDTO
import io.uvera.eobrazovanje.api.shared.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.api.shared.subjectExecution.dto.SubjectExecutionTableViewDTO
import io.uvera.eobrazovanje.api.shared.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.HeldExam
import io.uvera.eobrazovanje.common.repository.HeldExamResult
import java.time.LocalDate
import java.util.*

@EntityView(HeldExam::class)
interface HeldExamStudentViewDTO {
    var date: LocalDate
    @get:CollectionMapping
    val subjectExecution: EnrollmentViewDTO.SubjectExecutionViewDTO
}