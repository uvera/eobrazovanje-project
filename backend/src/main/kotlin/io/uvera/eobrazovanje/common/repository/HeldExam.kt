package io.uvera.eobrazovanje.common.repository

import java.time.LocalDate
import javax.persistence.*

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
