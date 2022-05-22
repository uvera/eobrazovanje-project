package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.common.repository.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "exam_enrollment")
class ExamEnrollment(
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "student_enrollment_id",
        nullable = false
    ) var student: Student,
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "execution_enrollment_id",
        nullable = false
    ) var subjectExecution: SubjectExecution,
    @Column(name = "attempt", nullable = false) var attempt: Int,
) : BaseEntity()