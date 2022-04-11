package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.updateEach
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectExecutionService(
    protected val repo: SubjectExecutionRepository,
    protected val subjectRepo: SubjectRepository,
    protected val preExamRepo: PreExamActivityRepository
) {
    @Transactional
    fun createSubjectExecution(subjectExecutionDTO: SubjectExecutionCreateDTO) = repo {
        val saved = subjectDTOToEntity(subjectExecutionDTO).save()
        preExamRepo {
            findAllById(subjectExecutionDTO.preExamActivityIds).updateEach { subjectExecution = saved }
        }
    }

    fun subjectDTOToEntity(dto: SubjectExecutionCreateDTO): SubjectExecution {
        return SubjectExecution(
            place = dto.place,
            time = dto.time,
            subject = subjectRepo.findByIdOrNull(dto.subjectId) ?: notFoundById<Subject>(dto.subjectId),
        )
    }
}