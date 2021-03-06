package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.shared.student.dto.EnrollmentViewDTO
import io.uvera.eobrazovanje.api.shared.teacher.dto.TeacherStudentViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject_enrollment")
@NamedEntityGraph(
    name = "enrollment-graph",
    attributeNodes = [
        NamedAttributeNode("subjectExecution"),
    ]
)
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
interface SubjectEnrollmentRepository : JpaSpecificationRepository<SubjectEnrollment, UUID> {
    @Query("select s from SubjectEnrollment s where s.student.id = :id and s.subjectExecution.subject.year = s.student.currentYear")
    @org.springframework.data.jpa.repository.EntityGraph("enrollment-graph")
    fun findAllByStudentId(page: Pageable, id: UUID): Page<EnrollmentViewDTO>

    @Query("select s from SubjectEnrollment s where s.student.id = :id and s.subjectExecution.subject.year = s.student.currentYear")
    @org.springframework.data.jpa.repository.EntityGraph("enrollment-graph")
    fun findAllByStudentIdForDisplay(id: UUID): List<EnrollmentViewDTO>

    @Query("select s from SubjectEnrollment s where s.student.id = :id and s.subjectExecution.subject.year = s.student.currentYear")
    @org.springframework.data.jpa.repository.EntityGraph("enrollment-graph")
    fun findAllByStudentId(id: UUID): List<SubjectEnrollment>

    @Query("select s from SubjectEnrollment s where s.subjectExecution.id = :id and s.subjectExecution.subject.year = s.student.currentYear")
    @org.springframework.data.jpa.repository.EntityGraph("enrollment-graph")
    fun findAllStudentsBySubjectExecution(page: Pageable, id: UUID): Page<TeacherStudentViewDTO>
}
