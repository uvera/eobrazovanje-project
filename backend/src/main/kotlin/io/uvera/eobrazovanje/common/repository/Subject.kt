package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.subject.dto.SubjectViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject")
class Subject(
    @Column(name = "espb", nullable = false)
    var espb: Int,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = true, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "study_program_id", nullable = true)
    var studyProgram: StudyProgram? = null,

    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    var subjectExecutions: MutableList<SubjectExecution> = mutableListOf(),

) : BaseEntity()

@Repository
interface SubjectRepository : JpaSpecificationRepository<Subject, UUID> {
    @Query("select t from Subject t where t.id = :id")
    fun findByIdAsDto(id: UUID): SubjectViewDTO?
    @Query("select s from Subject s")
    fun findAllAsDto(pageable: Pageable): Page<SubjectViewDTO>
    @Query("select s from Subject s where s.studyProgram is null")
    fun findAllWhereStudyProgramIsNullAsDto(): List<SubjectViewDTO>
    @Query("select s from Subject s")
    fun findAllSubjects(): List<SubjectViewDTO>
}
