package io.uvera.eobrazovanje.api.admin.subjectExecution

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionCreateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionUpdateDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.*
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
    protected val professorRepo: TeacherRepository,
    protected val studyRepo: StudyProgramRepository,
) {
    @Transactional
    fun createSubjectExecution(subjectExecutionDTO: SubjectExecutionCreateDTO): SubjectExecutionViewDTO = repo {
        subjectDTOToEntity(subjectExecutionDTO).save().also { subjectExEntity ->
            preExamRepo {
                findAllById(subjectExecutionDTO.preExamActivityIds).updateEach { subjectExecution = subjectExEntity }
            }
        }.also { subjectEx ->
            val studyProgramID = subjectRepo.findSubjectWithStudyProgram(subjectExecutionDTO.subjectId).studyProgram?.id
            repo {
                val dbStudy = studyProgramID?.let { studyRepo.findByIdWithExecutions(it) }
                subjectEx.studyProgram = dbStudy
                subjectEx.save()
            }
        }.let {
            getSubjectExecution(it.id)
        }
    }

    @Transactional
    fun updateSubjectExecution(id: UUID, dto: SubjectExecutionUpdateDTO): SubjectExecutionViewDTO = repo {
        val sub = findByIdOrNull(id) ?: notFoundById<SubjectExecution>(id)
        preExamRepo {
            sub.preExamActivities.filter {
                !dto.preExamActivityIds.contains(it.id)
            }.forEach { exam ->
                exam.subjectExecution = null
                exam.save()
            }
        }

        sub.update {
            place = dto.place
            time = dto.time
        }.let { subEntity ->
            preExamRepo {
                findAllById(dto.preExamActivityIds).updateEach {
                    subjectExecution = subEntity
                }
            }
        }

        return@repo findByIdAsDto(id) ?: notFoundById<SubjectExecution>(id)
    }

    @Transactional
    fun getAllSubjectExecutionsPaged(page: Int, records: Int): Any = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    @Transactional
    fun getAllSubjectExecutions() = repo {
        return@repo findAllForDisplay()
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
