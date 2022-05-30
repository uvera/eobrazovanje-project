package io.uvera.eobrazovanje.api.admin.announcement

import io.uvera.eobrazovanje.api.admin.announcement.dto.AnnouncementCreateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnnouncementService (
    protected val repo: AnnouncementRepository,
    protected val professorRepo: TeacherRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
) {
    @Transactional
    fun createAnnouncement(dto: AnnouncementCreateDTO) {
        val principal by principalDelegate()
        val announcement = Announcement(
            postText = dto.postText,
            subjectExecution = subjectExRepo.findByIdOrNull(dto.subjectExecutionId) ?: notFoundById<SubjectExecution>(dto.subjectExecutionId),
            teacher = professorRepo.findByTeacherUserEmail(principal.email) ?: notFoundByEmail<Teacher>(principal.email)
        )
        repo.save(announcement)
    }
}