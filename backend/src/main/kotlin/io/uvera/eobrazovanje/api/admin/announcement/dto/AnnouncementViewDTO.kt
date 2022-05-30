package io.uvera.eobrazovanje.api.admin.announcement

import com.blazebit.persistence.view.CollectionMapping
import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Announcement
import io.uvera.eobrazovanje.common.repository.SubjectExecution
import java.time.LocalTime
import java.util.UUID

@EntityView(Announcement::class)
interface AnnouncementViewDTO {
    var id: UUID
    var postText: String
}