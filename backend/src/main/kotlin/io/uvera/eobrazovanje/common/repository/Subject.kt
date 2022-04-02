package io.uvera.eobrazovanje.common.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "subject")
class Subject(
    @Column(name = "espb", nullable = false)
    var espb: Int,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "year", nullable = false)
    var year: Int,
    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    var subjectExecutions: MutableList<SubjectExecution> = mutableListOf(),
) : BaseEntity()
