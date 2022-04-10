package io.uvera.eobrazovanje.api.admin.studyprogram

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.common.repository.StudyProgram
import io.uvera.eobrazovanje.common.repository.StudyProgramRepository
import io.uvera.eobrazovanje.common.repository.SubjectRepository
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudyProgramService(
    protected val repo: StudyProgramRepository,
    protected val subjectRepo: SubjectRepository,
) {
    fun createStudyProgram(studyProgramDTO: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        studyProgramDTOToEntity(studyProgramDTO).save().also { studyProgramEntity ->
            subjectRepo {
                findAllById(studyProgramDTO.subjects).onEach { subject ->
                    subject.tap {
                        studyProgram = studyProgramEntity
                    }
                }.saveAll()
            }
        }.asDTO()
    }

    fun updateStudyProgram(id: UUID, dto: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        val dbStudy = findByIdOrNull(id) ?: notFoundById<StudyProgram>(id)
        dbStudy.update {
            name = dto.name
            codeName = dto.codeName
        }.let { studyProgramEntity ->
            subjectRepo {
                findAllById(dto.subjects).updateEach {
                    studyProgram = studyProgramEntity
                }
            }
        }
        return@repo findByIdAsDto(id) ?: notFoundById<StudyProgram>(id)
    }

    fun deleteStudyProgram(id: UUID) = repo {
        if (!existsById(id)) notFoundById<StudyProgram>(id)
        return@repo deleteById(id)
    }

    fun getStudyProgram(studyProgramId: UUID): StudyProgramViewDTO =
        repo.findByIdAsDto(studyProgramId) ?: notFoundById<StudyProgram>(studyProgramId)

    context(StudyProgramRepository)
    fun StudyProgram.asDTO() = this.let { findByIdAsDto(it.id) ?: notFoundById<StudyProgram>(it.id) }

    fun studyProgramDTOToEntity(dto: StudyProgramCreateDTO): StudyProgram {
        return StudyProgram(
            codeName = dto.codeName,
            name = dto.name
        )
    }

}
