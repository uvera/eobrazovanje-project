package io.uvera.eobrazovanje.api.admin.examPeriod

import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ExamPeriodService(
    protected val repo: ExamPeriodRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
) {

    fun createExamPeriod(dto: ExamPeriodCreateDTO) = repo {
        dtoToEntity(dto).save()
    }

    fun dtoToEntity(dto: ExamPeriodCreateDTO): ExamPeriod {
        return ExamPeriod(
            name = dto.name,
            endDate = dto.endDate,
            startDate = dto.startDate,
            subjectExecutions = subjectExRepo.findAllById(dto.subjectExecutionIds).toMutableSet()
        )
    }
}