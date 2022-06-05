package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.shared.examPeriod.dto.ExamPeriodViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "exam_period")
@NamedEntityGraph(
    name = "exam-graph",
    attributeNodes = [
        NamedAttributeNode("subjectExecutions"),
    ]
)
class ExamPeriod(
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "end_date", nullable = false) var endDate: LocalDate,
    @Column(name = "start_date", nullable = false) var startDate: LocalDate,
    @ManyToMany(mappedBy = "examPeriods")
    var subjectExecutions: MutableSet<SubjectExecution> = mutableSetOf(),
    @OneToMany(mappedBy = "examPeriod", orphanRemoval = true)
    var heldExams: MutableList<HeldExam> = mutableListOf(),
    @OneToMany(mappedBy = "examPeriod", orphanRemoval = true)
    var examEnrollments: MutableSet<ExamEnrollment> = mutableSetOf()
) : BaseEntity()

@Repository
interface ExamPeriodRepository : JpaSpecificationRepository<ExamPeriod, UUID> {
    @Query("select t from ExamPeriod t left join fetch t.subjectExecutions where t.id = :id")
    fun findByIdAsDto(id: UUID): ExamPeriodViewDTO?

    @org.springframework.data.jpa.repository.EntityGraph("exam-graph")
    @Query("select t from ExamPeriod t")
    fun findAllForStudent(): List<ExamPeriodViewDTO>

    @org.springframework.data.jpa.repository.EntityGraph("exam-graph")
    @Query("select t from ExamPeriod t")
    fun findAllPaged(page: Pageable): Page<ExamPeriodViewDTO>

    @Query("select t from ExamPeriod t where t.id = :examPeriodID")
    @org.springframework.data.jpa.repository.EntityGraph("exam-graph")
    fun findByExamPeriodID(examPeriodID: UUID): ExamPeriod?
}
