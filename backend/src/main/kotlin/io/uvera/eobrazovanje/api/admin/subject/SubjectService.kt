package io.uvera.eobrazovanje.api.admin.subject

import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectCreateDTO
import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.common.repository.Subject
import io.uvera.eobrazovanje.common.repository.SubjectRepository
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.update
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubjectService(
    protected val repo: SubjectRepository,
) {

    fun createSubject(subjectDTO: SubjectCreateDTO): SubjectViewDTO = repo {
        getSubject(subjectDTOToEntity(subjectDTO).save().id)
    }

    fun updateSubject(id: UUID, dto: SubjectCreateDTO): SubjectViewDTO = repo {
        val dbSubject = findByIdOrNull(id) ?: notFoundById<Subject>(id)
        dbSubject.update {
            name = dto.name
            year = dto.year
            espb = dto.espb
        }
        return@repo findByIdAsDto(id) ?: notFoundById<Subject>(id)
    }

    fun deleteSubject(id: UUID) = repo {
        if (!existsById(id)) notFoundById<Subject>(id)
        return@repo deleteById(id)
    }

    fun getSubject(subjectId: UUID): SubjectViewDTO =
        repo.findByIdAsDto(subjectId) ?: notFoundById<Subject>(subjectId)

    context(SubjectRepository)
    fun Subject.asDTO() = this.let { findByIdAsDto(it.id) ?: notFoundById<Subject>(it.id) }

    fun subjectDTOToEntity(dto: SubjectCreateDTO): Subject {
        return Subject(
            name = dto.name,
            espb = dto.espb,
            year = dto.year
        )
    }

}
