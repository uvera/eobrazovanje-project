package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.updateEach
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubjectExecutionService(
    protected val repo: SubjectExecutionRepository,
    protected val subjectRepo: SubjectRepository,
    protected val preExamRepo: PreExamActivityRepository
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
    fun getSubjectExecution(studyProgramId: UUID): SubjectExecutionViewDTO =
        repo.findByIdAsDto(studyProgramId) ?: notFoundById<StudyProgram>(studyProgramId)

    fun subjectDTOToEntity(dto: SubjectExecutionCreateDTO): SubjectExecution {
        return SubjectExecution(
            place = dto.place,
            time = dto.time,
            subject = subjectRepo.findByIdOrNull(dto.subjectId) ?: notFoundById<Subject>(dto.subjectId),
        )
    }
}
