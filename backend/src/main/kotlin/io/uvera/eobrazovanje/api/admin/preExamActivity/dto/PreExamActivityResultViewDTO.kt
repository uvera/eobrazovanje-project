package io.uvera.eobrazovanje.api.admin.preExamActivity.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.PreExamActivityResult
import java.util.*

@EntityView(PreExamActivityResult::class)
interface PreExamActivityResultViewDTO {
    var id: UUID
    var score: Int
    var created: Boolean
    var preExamActivity: PreExamActivityViewDTO
}