package io.uvera.eobrazovanje.api.shared.announcement

import io.uvera.eobrazovanje.api.shared.announcement.dto.AnnouncementCreateDTO
import io.uvera.eobrazovanje.api.shared.announcement.dto.AnnouncementViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.notFoundByEmail
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.principalDelegate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AnnouncementService (
    protected val repo: AnnouncementRepository,
    protected val professorRepo: TeacherRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
    protected val studentRepository: StudentRepository
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

    @Transactional
    fun getAnnouncementsByPrincipalAndSubjectExecution(page: Int, records: Int, subjExId: UUID): Page<AnnouncementViewDTO> {
        val req = PageRequest.of(page - 1, records)
        return repo.findAllBySubjectExecutionId(req, subjExId)
    }
}