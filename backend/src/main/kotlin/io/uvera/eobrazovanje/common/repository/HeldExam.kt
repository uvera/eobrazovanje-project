package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.heldExam.dto.HeldExamViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import java.time.LocalDate
import java.util.UUID
import javax.persistence.*
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Entity
@Table(name = "held_exam")
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
    fun findByExamPeriodAndSubjectExecution(examPeriodID: UUID, subjExId: UUID): HeldExamViewDTO?
}
