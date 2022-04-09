package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.studyprogram.dto.StudyProgramViewDTO
import io.uvera.eobrazovanje.api.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
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
    var studyProgramEnrollments: MutableList<StudyProgramEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "studyProgram", orphanRemoval = true)
    var subjects: MutableList<Subject> = mutableListOf(),
) : BaseEntity()

@Repository
interface StudyProgramRepository : JpaSpecificationRepository<StudyProgram, UUID> {
    @Query("select t from StudyProgram t left join fetch t.subjects where t.id = :id")
    fun findByIdAsDto(id: UUID): StudyProgramViewDTO?
}