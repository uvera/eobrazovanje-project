package io.uvera.eobrazovanje.common.repository

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "exam_period")
class ExamPeriod(
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "end_date", nullable = false) var endDate: LocalDate,
    @Column(name = "start_date", nullable = false) var startDate: LocalDate,
    @ManyToMany(mappedBy = "examPeriods")
    var subjectExecutions: MutableSet<SubjectExecution> = mutableSetOf(),
    @OneToMany(mappedBy = "examPeriod", orphanRemoval = true)
    var heldExams: MutableList<HeldExam> = mutableListOf()
) : BaseEntity()
