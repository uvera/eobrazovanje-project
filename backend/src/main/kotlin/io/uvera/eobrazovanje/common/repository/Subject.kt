package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "subject")
class Subject(
    @Column(name = "espb", nullable = false)
    var espb: Int,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_program_id", nullable = false)
    var studyProgram: StudyProgram,

    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    var subjectExecutions: MutableList<SubjectExecution> = mutableListOf(),

) : BaseEntity()
