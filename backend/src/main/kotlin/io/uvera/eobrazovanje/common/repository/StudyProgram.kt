package io.uvera.eobrazovanje.common.repository

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
) : BaseEntity()
