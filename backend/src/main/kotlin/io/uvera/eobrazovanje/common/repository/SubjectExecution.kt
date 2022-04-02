package io.uvera.eobrazovanje.common.repository

import java.time.LocalDateTime
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
