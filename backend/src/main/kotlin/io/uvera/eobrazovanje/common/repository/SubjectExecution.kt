package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
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
    @ManyToMany(mappedBy = "subjectExecutions")
    var studyPrograms: MutableSet<StudyProgram> = mutableSetOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var subjectProfessorEnrollments: MutableList<SubjectProfessorEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "subjectExecution", orphanRemoval = true)
    var preExamActivities: MutableList<PreExamActivity> = mutableListOf(),

    @ManyToMany
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
}