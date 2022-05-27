package io.uvera.eobrazovanje.api.admin.studyprogram

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramCreateDTO
import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudyProgramService(
    protected val repo: StudyProgramRepository,
    protected val subjectRepo: SubjectRepository,
    protected val studentRepo: StudentRepository
) {

    @Transactional(propagation = Propagation.NEVER)
    fun createStudyProgram(studyProgramDTO: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        studyProgramDTOToEntity(studyProgramDTO).save().also { studyProgramEntity ->
            subjectRepo {
                findAllById(studyProgramDTO.subjects).updateEach {
                    studyProgram = studyProgramEntity
                }
            }
        }.let {
            getStudyProgram(it.id)
        }
    }

    @Transactional
    fun updateStudyProgram(id: UUID, dto: StudyProgramCreateDTO): StudyProgramViewDTO = repo {
        val dbStudy = findByIdOrNull(id) ?: notFoundById<StudyProgram>(id)
        subjectRepo {
            dbStudy.subjects.filter {
                !dto.subjects.contains(it.id)
            }.forEach { subject ->
                subject.studyProgram = null
                subject.save()
            }
        }

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

    @Transactional
    fun deleteStudyProgram(id: UUID) = repo {
        if (!existsById(id)) notFoundById<StudyProgram>(id)
        return@repo deleteById(id)
    }

    @Transactional
    fun getStudyProgram(studyProgramId: UUID): StudyProgramViewDTO =
        repo.findByIdAsDto(studyProgramId) ?: notFoundById<StudyProgram>(studyProgramId)

    fun studyProgramDTOToEntity(dto: StudyProgramCreateDTO): StudyProgram {
        return StudyProgram(
            codeName = dto.codeName,
            name = dto.name
        )
    }

    @Transactional
    fun getAllStudyProgramsPaged(page: Int, records: Int): Any = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    @Transactional
    fun enrollStudentsToStudyProgram(studyProgramCode: String, studentIds: List<UUID>): Any = repo {
        val dbStudy = findByCodeName(studyProgramCode) ?: notFoundByCodeName<StudyProgram>(studyProgramCode)
        studentRepo {
                studentIds.forEach {
                    val student = studentRepo.findByIdOrNull(it) ?: notFoundById<Student>(it)
                    student.studyProgram = dbStudy
                    student.currentYear = 1
                    student.save()
                }
            }
        }
    }

