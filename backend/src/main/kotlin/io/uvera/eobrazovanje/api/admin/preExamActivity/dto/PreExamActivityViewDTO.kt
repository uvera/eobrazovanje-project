package io.uvera.eobrazovanje.api.admin.preExamActivity.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.common.repository.PreExamActivity
import java.util.*

@EntityView(PreExamActivity::class)
interface PreExamActivityViewDTO {
    var id: UUID
    var name: String
    var points: Int
}
