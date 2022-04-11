package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pre_exam_activity")
class PreExamActivity(
    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne(optional = true)
    @JoinColumn(name = "subject_execution_id", nullable = true)
    var subjectExecution: SubjectExecution? = null,

    @OneToMany(mappedBy = "preExamActivity", orphanRemoval = true)
    var preExamActivityResults: MutableList<PreExamActivityResult> = mutableListOf()
) : BaseEntity()

@Repository
interface PreExamActivityRepository : JpaSpecificationRepository<PreExamActivity, UUID>
