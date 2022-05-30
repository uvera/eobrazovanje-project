package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.heldExam.dto.HeldExamViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "held_exam_result")
class HeldExamResult(
    @Column(name = "score", nullable = false) var score: Int,
    @ManyToOne(optional = false)
    @JoinColumn(name = "held_exam_id", nullable = false)
    var heldExam: HeldExam,
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,
) : BaseEntity()

@Repository
interface HeldExamResultRepository : JpaSpecificationRepository<HeldExamResult, UUID>