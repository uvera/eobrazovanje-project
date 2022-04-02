package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "study_program_enrollment")
class StudyProgramEnrollment(
    @Column(name = "paying_tuition", nullable = false)
    var payingTuition: Boolean,

    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = false)
    @JoinColumn(name = "study_program_id")
    var studyProgram: StudyProgram,

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()
