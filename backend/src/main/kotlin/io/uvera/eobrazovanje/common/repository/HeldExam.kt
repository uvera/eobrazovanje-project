package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.shared.heldExam.dto.HeldExamViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "held_exam")
@NamedEntityGraph(
    name = "held-exam-graph",
    attributeNodes = [
        NamedAttributeNode("heldExamResults"),
    ]
)
class HeldExam(
    @Column(name = "date", nullable = false) var date: LocalDate,
    @ManyToOne(optional = false) @JoinColumn(
        name = "exam_period_id",
        nullable = false
    ) var examPeriod: ExamPeriod,
    @ManyToOne(optional = false) @JoinColumn(
        name = "subject_execution_id",
        nullable = false
    ) var subjectExecution: SubjectExecution,
    @OneToMany(
        mappedBy = "heldExam",
        orphanRemoval = true
    ) var heldExamResults: MutableList<HeldExamResult> = mutableListOf(),

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    var teacher: Teacher,
) : BaseEntity()

@Repository
interface HeldExamRepository : JpaSpecificationRepository<HeldExam, UUID> {
    @Query("select t from HeldExam t where t.examPeriod.id = :examPeriodID and t.subjectExecution.id = :subjExId")
    @org.springframework.data.jpa.repository.EntityGraph("held-exam-graph")
    fun findByExamPeriodAndSubjectExecution(examPeriodID: UUID, subjExId: UUID): HeldExamViewDTO?

    @Query("select t from HeldExam t")
    @org.springframework.data.jpa.repository.EntityGraph("held-exam-graph")
    fun findByIdAsDto(heldExamId: UUID): HeldExamViewDTO
}
