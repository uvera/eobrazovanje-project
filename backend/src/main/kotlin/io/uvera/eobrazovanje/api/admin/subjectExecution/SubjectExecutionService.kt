package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SubjectExecutionService(
    protected val repo: SubjectExecutionRepository,
    protected val subjectRepo: SubjectRepository,
    protected val preExamRepo: PreExamActivityRepository
) {

    fun createSubjectExecution(subjectExecutionDTO: SubjectExecutionCreateDTO) = repo {
        subjectDTOToEntity(subjectExecutionDTO).save()
    }

    fun subjectDTOToEntity(dto: SubjectExecutionCreateDTO): SubjectExecution {
        return SubjectExecution(
            place = dto.place,
            time = dto.time,
            subject = subjectRepo.findByIdOrNull(dto.subjectId) ?: notFoundById<Subject>(dto.subjectId),
            preExamActivities = preExamRepo.findAllById(dto.preExamActivityIds)
        )
    }
}