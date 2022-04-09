package io.uvera.eobrazovanje.common.repository

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "student_document")
class StudentDocument(
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "valid_till", nullable = false) var validTill: LocalDate,
    @Column(name = "registered_at", nullable = false) var registeredAt: LocalDate,
    @Column(name = "type", nullable = false) var type: String,
    @OneToMany(
        mappedBy = "studentDocument", orphanRemoval = true
    ) var documentFiles: MutableList<DocumentFile> = mutableListOf()
) : BaseEntity()
