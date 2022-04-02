package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "announcement")
class Announcement(
    @Lob @Column(name = "post_text", nullable = false) var postText: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_execution_id", nullable = false)
    var subjectExecution: SubjectExecution,

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    var teacher: Teacher,

    @OneToMany(mappedBy = "announcement", orphanRemoval = true)
    var announcementComments: MutableList<AnnouncementComment> = mutableListOf(),
) : BaseEntity()
