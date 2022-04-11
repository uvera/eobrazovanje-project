package io.uvera.eobrazovanje.api.admin.examPeriod

import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodCreateDTO
import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.updateEach
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ExamPeriodService(
    protected val repo: ExamPeriodRepository,
    protected val subjectExRepo: SubjectExecutionRepository,
) {

    @Transactional(propagation = Propagation.NEVER)
    fun createExamPeriod(dto: ExamPeriodCreateDTO): ExamPeriodViewDTO = repo {
        dtoToEntity(dto).save().also { examEntity ->
            subjectExRepo {
                findAllByIds(dto.subjectExecutionIds).updateEach {
                    examPeriods.add(examEntity)
                }
            }
        }.let {
            getExamPeriod(it.id)
        }
    }

    @Transactional
    fun getExamPeriod(examPeriodID: UUID): ExamPeriodViewDTO =
        repo.findByIdAsDto(examPeriodID) ?: notFoundById<ExamPeriod>(examPeriodID)

    fun dtoToEntity(dto: ExamPeriodCreateDTO): ExamPeriod {
        return ExamPeriod(
            name = dto.name,
            endDate = dto.endDate,
            startDate = dto.startDate,
            subjectExecutions = subjectExRepo.findAllById(dto.subjectExecutionIds).toMutableSet()
        )
    }
}
