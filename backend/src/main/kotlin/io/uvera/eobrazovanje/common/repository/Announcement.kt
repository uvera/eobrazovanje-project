package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
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

@Repository
interface AnnouncementRepository : JpaSpecificationRepository<Announcement, UUID>
