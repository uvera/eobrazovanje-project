package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.updateEach
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubjectExecutionService(
    protected val repo: SubjectExecutionRepository,
    protected val subjectRepo: SubjectRepository,
    protected val preExamRepo: PreExamActivityRepository,
    protected val professorRepo: TeacherRepository
) {
    @Transactional(propagation = Propagation.NEVER)
    fun createSubjectExecution(subjectExecutionDTO: SubjectExecutionCreateDTO): SubjectExecutionViewDTO = repo {
        subjectDTOToEntity(subjectExecutionDTO).save().also { subjectExEntity ->
            preExamRepo {
                findAllById(subjectExecutionDTO.preExamActivityIds).updateEach { subjectExecution = subjectExEntity }
            }
        }.let {
            getSubjectExecution(it.id)
        }
    }

    @Transactional
    fun getAllSubjectExecutionsPaged(page: Int, records: Int): Any = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    @Transactional
    fun getSubjectExecution(subjExecutionId: UUID): SubjectExecutionViewDTO =
        repo.findByIdAsDto(subjExecutionId) ?: notFoundById<SubjectExecution>(subjExecutionId)

    fun subjectDTOToEntity(dto: SubjectExecutionCreateDTO): SubjectExecution {
        return SubjectExecution(
            place = dto.place,
            time = dto.time,
            subject = subjectRepo.findByIdOrNull(dto.subjectId) ?: notFoundById<Subject>(dto.subjectId),
        )
    }
}
