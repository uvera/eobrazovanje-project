package io.uvera.eobrazovanje.api.admin.announcement.dto

import com.blazebit.persistence.view.EntityView
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.common.repository.Announcement
import java.util.*

@EntityView(Announcement::class)
interface AnnouncementViewDTO {
    var id: UUID
    var postText: String
    var teacher: TeacherResponseDTO
}