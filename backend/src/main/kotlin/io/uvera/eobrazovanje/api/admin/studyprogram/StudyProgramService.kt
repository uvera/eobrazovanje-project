package io.uvera.eobrazovanje.api.admin.studyprogram

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.api.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.api.teacher.dto.TeacherUpdateDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudyProgramService(
    protected val repo: StudyProgramRepository,
    protected val subjectRepo: SubjectRepository,
) {
    fun createStudyProgram(studyProgram: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        studyProgramDTOToEntity(studyProgram).save().asDTO()
    }

    fun updateStudyProgram(id: UUID, studyProgram: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        val dbStudy = findByIdOrNull(id) ?: notFoundById<StudyProgram>(id)
        dbStudy.let {
            val newStudy = studyProgramDTOToEntity(studyProgram)
            it.name = newStudy.name
            it.subjects = newStudy.subjects
            it.codeName = newStudy.codeName
            it.save()
        }
        return@repo findByIdAsDto(id) ?: notFoundById<StudyProgram>(id)
    }

    fun deleteStudyProgram(id: UUID) = repo {
        val study = findByIdOrNull(id) ?: notFoundById<StudyProgram>(id)
        return@repo delete(study)
    }

    fun getStudyProgram(studyProgramId: UUID): StudyProgramViewDTO =
        repo.findByIdAsDto(studyProgramId) ?: notFoundById<StudyProgram>(studyProgramId)

    context(StudyProgramRepository)
    fun StudyProgram.asDTO() = this.let { findByIdAsDto(it.id) ?: notFoundById<StudyProgram>(it.id) }

    fun studyProgramDTOToEntity(dto: StudyProgramCreateDTO): StudyProgram {
        return StudyProgram(
            codeName = dto.codeName,
            name = dto.name,
            subjects = mapSubjectsFromDTO(dto.subjects).toMutableList()
        )
    }

    fun mapSubjectsFromDTO(idList: List<UUID>): List<Subject> = subjectRepo {
        findAllById(idList)
    }
}
