package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "document_file")
class DocumentFile(
    @Column(name = "ordering", nullable = false) var ordering: Int,
    @Column(name = "extension", nullable = false) var extension: String,
    @Column(name = "name", nullable = false) var name: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_document_id", nullable = false)
    var studentDocument: StudentDocument,
) : BaseEntity()
