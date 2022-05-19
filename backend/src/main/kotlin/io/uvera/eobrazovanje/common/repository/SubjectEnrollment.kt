package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject_enrollment")
class SubjectEnrollment(
    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = false) @JoinColumn(
        name = "subject_execution_id",
        nullable = false
    )
    var subjectExecution: SubjectExecution,

    @ManyToOne(optional = false) @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()

@Repository
interface SubjectEnrollmentRepository : JpaSpecificationRepository<SubjectEnrollment, UUID>