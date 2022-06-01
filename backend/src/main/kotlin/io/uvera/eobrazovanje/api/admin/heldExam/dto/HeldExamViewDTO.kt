package io.uvera.eobrazovanje.api.admin.heldExam.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.HeldExam
import java.time.LocalDate

@EntityView(HeldExam::class)
interface HeldExamViewDTO {
    var date: LocalDate
}