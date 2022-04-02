package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "subject_enrollment")
class SubjectEnrollment(
    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = false) @JoinColumn(name = "subject_execution_id",
        nullable = false)
    var subjectExecution: SubjectExecution,

    @ManyToOne(optional = false) @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()
