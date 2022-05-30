package io.uvera.eobrazovanje.api.admin.announcement

import io.uvera.eobrazovanje.api.admin.announcement.dto.AnnouncementCreateDTO
import io.uvera.eobrazovanje.api.admin.announcement.dto.AnnouncementViewDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.api.admin.announcement.AnnouncementController
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.updateEach
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnnouncementService(
    protected val repo: AnnouncementRepository,
    protected val professorRepo: TeacherRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
)

@Transactional
fun createAnnouncement(dto: AnnouncementCreateDTO): AnnouncementViewDTO = repo {
    dtoToEntity(dto).save().also { announcementEntity ->
        subjectExRepo {
            findAllByIds(dto.subjectExecutionIds).updateEach {
                announcements.add(announcementEntity)
            }
        }
    }.let {
        getAnnouncement(it.id)
    }
}