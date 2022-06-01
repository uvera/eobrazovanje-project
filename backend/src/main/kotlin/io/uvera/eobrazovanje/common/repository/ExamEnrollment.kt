package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.examPeriod.dto.ExamEnrollmentDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "exam_enrollment")
@NamedEntityGraph(
    name = "enroll-graph",
    attributeNodes = [
        NamedAttributeNode("student"),
        NamedAttributeNode("examPeriod"),
        NamedAttributeNode("subjectExecution")
    ]
)
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
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "exam_period_enrollment_id",
        nullable = false
    ) var examPeriod: ExamPeriod,
) : BaseEntity()

@Repository
interface ExamEnrollmentRepository : JpaSpecificationRepository<ExamEnrollment, UUID> {

    @org.springframework.data.jpa.repository.EntityGraph("enroll-graph")
    @Query("select t from ExamEnrollment t where t.examPeriod.id = :id")
    fun findByExamPeriodForStudent(id: UUID): List<ExamEnrollment>

    @org.springframework.data.jpa.repository.EntityGraph("enroll-graph")
    @Query("select t from ExamEnrollment t where t.student.id = :id")
    fun findByExamPeriodForStudent(page: Pageable, id: UUID): Page<ExamEnrollmentDTO>

    @org.springframework.data.jpa.repository.EntityGraph("enroll-graph")
    @Query("select t from ExamEnrollment t where t.examPeriod.id = :examPeriodID and t.subjectExecution.id = :subjExId")
    fun findByExamPeriodAndSubjectExecution(examPeriodID: UUID, subjExId: UUID): List<ExamEnrollment>
}
