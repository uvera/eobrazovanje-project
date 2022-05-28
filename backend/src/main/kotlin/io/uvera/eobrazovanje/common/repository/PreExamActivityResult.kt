package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityResultViewDTO
import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pre_exam_activity_result")
@NamedEntityGraph(
    name = "pre-exam-result-graph",
    attributeNodes = [
        NamedAttributeNode("student"),
        NamedAttributeNode("preExamActivity")
    ]
)
class PreExamActivityResult(
    @Column(name = "score")
    var score: Int,

    @ManyToOne(optional = false)
    @JoinColumn(name = "pre_exam_activity_id", nullable = false)
    var preExamActivity: PreExamActivity,

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()

@Repository
interface PreExamActivityResultRepository : JpaSpecificationRepository<PreExamActivityResult, UUID> {

    @Query("select t from PreExamActivityResult t where t.student.id = :id and t.preExamActivity.id in :preExamIds")
    @org.springframework.data.jpa.repository.EntityGraph("pre-exam-graph")
    fun findAllByStudent(id: UUID, preExamIds: List<UUID>): List<PreExamActivityResultViewDTO>
}
