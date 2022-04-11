package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject_professor_enrollment")
class SubjectProfessorEnrollment(
    @ManyToOne @JoinColumn(name = "subject_execution_id")
    var subjectExecution: SubjectExecution,
    @Column(name = "year", nullable = false)
    var year: Int,
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    var teacher: Teacher,
) : BaseEntity()

@Repository
interface SubjectProfessorEnrollmentRepository : JpaSpecificationRepository<SubjectProfessorEnrollment, UUID>
