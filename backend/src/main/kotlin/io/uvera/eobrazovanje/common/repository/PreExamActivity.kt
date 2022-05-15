package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pre_exam_activity")
class PreExamActivity(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "points", nullable = false)
    var points: Int,

    @ManyToOne(optional = true)
    @JoinColumn(name = "subject_execution_id", nullable = true)
    var subjectExecution: SubjectExecution? = null,

    @OneToMany(mappedBy = "preExamActivity", orphanRemoval = true)
    var preExamActivityResults: MutableList<PreExamActivityResult> = mutableListOf()
) : BaseEntity()

@Repository
interface PreExamActivityRepository : JpaSpecificationRepository<PreExamActivity, UUID> {
    @Query("select t from PreExamActivity t where t.id = :id")
    fun findByIdAsDto(id: UUID): PreExamActivityViewDTO?

    @Query("select t from PreExamActivity t")
    fun findAllAsDto(page: Pageable): Page<PreExamActivityViewDTO>

    @Query("select t from PreExamActivity t")
    fun findAllPreExamActivities(): List<PreExamActivityViewDTO>
}
