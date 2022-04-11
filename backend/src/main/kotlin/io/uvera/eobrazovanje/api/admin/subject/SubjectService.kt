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
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubjectService(
    protected val repo: SubjectRepository,
) {

    @Transactional(propagation = Propagation.NEVER)
    fun createSubject(subjectDTO: SubjectCreateDTO): SubjectViewDTO = repo {
        getSubject(subjectDTOToEntity(subjectDTO).save().id)
    }

    @Transactional(propagation = Propagation.NEVER)
    fun updateSubject(id: UUID, dto: SubjectCreateDTO): SubjectViewDTO = repo {
        val dbSubject = findByIdOrNull(id) ?: notFoundById<Subject>(id)
        dbSubject.update {
            name = dto.name
            year = dto.year
            espb = dto.espb
        }
        return@repo getSubject(id)
    }

    fun deleteSubject(id: UUID) = repo {
        if (!existsById(id)) notFoundById<Subject>(id)
        return@repo deleteById(id)
    }

    fun getSubject(subjectId: UUID): SubjectViewDTO =
        repo.findByIdAsDto(subjectId) ?: notFoundById<Subject>(subjectId)

    fun subjectDTOToEntity(dto: SubjectCreateDTO): Subject {
        return Subject(
            name = dto.name,
            espb = dto.espb,
            year = dto.year
        )
    }
}
