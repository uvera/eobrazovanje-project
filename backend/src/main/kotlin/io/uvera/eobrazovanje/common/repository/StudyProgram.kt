package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.*

@Entity
@NamedEntityGraph(
    name = "StudyProgram.subjects",
    attributeNodes = [NamedAttributeNode("subjects")]
)
@Table(name = "study_program")
class StudyProgram(
    @Column(name = "code_name", nullable = false, unique = true)
    var codeName: String,
    @Column(name = "name", nullable = false)
    var name: String,
    @ManyToMany
    @JoinTable(
        name = "study_program_subject_executions",
        joinColumns = [JoinColumn(name = "study_program_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_executions_id")]
    )
    var subjectExecutions: MutableSet<SubjectExecution> = mutableSetOf(),

    @OneToMany(mappedBy = "studyProgram", orphanRemoval = true)
    var students: MutableList<Student> = mutableListOf(),

    @OneToMany(mappedBy = "studyProgram", orphanRemoval = true)
    var subjects: MutableList<Subject> = mutableListOf(),
) : BaseEntity()

@Repository
@Transactional(readOnly = true)
interface StudyProgramRepository : JpaSpecificationRepository<StudyProgram, UUID> {
    @Query("select t from StudyProgram t left join fetch t.subjects where t.id = :id")
    fun findByIdAsDto(id: UUID): StudyProgramViewDTO?

    @org.springframework.data.jpa.repository.EntityGraph("StudyProgram.subjects")
    @Query(
        "select t from StudyProgram t",
    )
    fun findAllAsDto(page: Pageable): Page<StudyProgramViewDTO>
}
