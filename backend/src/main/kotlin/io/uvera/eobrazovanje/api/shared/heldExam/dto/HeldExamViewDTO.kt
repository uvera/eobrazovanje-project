package io.uvera.eobrazovanje.api.shared.heldExam.dto

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.shared.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.common.repository.HeldExam
import io.uvera.eobrazovanje.common.repository.HeldExamResult
import java.time.LocalDate
import java.util.UUID

@EntityView(HeldExam::class)
interface HeldExamViewDTO {
    var id: UUID
    var date: LocalDate
    @get:CollectionMapping
    val heldExamResults: List<HeldExamResultViewDTO>

    @EntityView(HeldExamResult::class)
    interface HeldExamResultViewDTO {
        var student: StudentViewDTO
        var score: Int
    }
}
