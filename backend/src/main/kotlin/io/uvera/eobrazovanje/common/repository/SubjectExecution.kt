package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionTableViewDTO
import io.uvera.eobrazovanje.api.admin.subjectExecution.dto.SubjectExecutionViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@NamedEntityGraph(
    name = "subject-entity-graph",
    attributeNodes = [
        NamedAttributeNode("preExamActivities"),
        NamedAttributeNode("subject"),
    ]
)
@Table(name = "subject_execution")
class SubjectExecution(
    @Column(name = "place", nullable = false)
    var place: String,
    @Column(name = "time", nullable = false)
    var time: LocalDateTime,
    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    var subject: Subject,
    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var subjectEnrollments: MutableList<SubjectEnrollment> = mutableListOf(),

    @ManyToOne(optional = true)
    @JoinColumn(name = "study_program_subject_id", nullable = true)
    var studyProgram: StudyProgram? = null,

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var subjectProfessorEnrollments: MutableList<SubjectProfessorEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var preExamActivities: MutableList<PreExamActivity> = mutableListOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var studentExamEnrollments: MutableList<ExamEnrollment> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "subject_execution_exam_periods",
        joinColumns = [JoinColumn(name = "subject_execution_id")],
        inverseJoinColumns = [JoinColumn(name = "exam_periods_id")]
    )
    var examPeriods: MutableList<ExamPeriod> = mutableListOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var heldExams: MutableList<HeldExam> = mutableListOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var announcements: MutableList<Announcement> = mutableListOf(),
) : BaseEntity()

@Repository
interface SubjectExecutionRepository : JpaSpecificationRepository<SubjectExecution, UUID> {
    @Query(
        """ select se from SubjectExecution se 
            left outer join fetch se.examPeriods
            where se.id in :ids
        """
    )
    fun findAllByIds(ids: List<UUID>): MutableList<SubjectExecution>

    @org.springframework.data.jpa.repository.EntityGraph("subject-entity-graph")
    @Query(
        "select t from SubjectExecution t where t.id = :id",
    )
    fun findByIdAsDto(id: UUID): SubjectExecutionViewDTO?

    @Query("select t from SubjectExecution t")
    fun findAllAsDto(page: Pageable): Page<SubjectExecutionTableViewDTO>

    @org.springframework.data.jpa.repository.EntityGraph("subject-entity-graph")
    @Query(
        "select t from SubjectExecution t",
    )
    fun findAllForDisplay(): List<SubjectExecutionViewDTO>
}
