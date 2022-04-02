package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "pre_exam_activity_result")
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
