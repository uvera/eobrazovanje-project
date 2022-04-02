package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "pre_exam_activity")
class PreExamActivity(
    @Column(name = "name", nullable = false) var name: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_execution_id", nullable = false)
    var subjectExecution: SubjectExecution,

    @OneToMany(mappedBy = "preExamActivity", orphanRemoval = true)
    var preExamActivityResults: MutableList<PreExamActivityResult> = mutableListOf()
) : BaseEntity()
