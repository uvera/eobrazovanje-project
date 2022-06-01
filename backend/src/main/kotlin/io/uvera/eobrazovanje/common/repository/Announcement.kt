package io.uvera.eobrazovanje.common.repository

import org.springframework.data.jpa.repository.Query
import io.uvera.eobrazovanje.api.admin.announcement.dto.AnnouncementViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "announcement")
@NamedEntityGraph(
    name = "announcement-graph",
    attributeNodes = [
        NamedAttributeNode("subjectExecution"),
        NamedAttributeNode("teacher")
    ]
)
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
interface AnnouncementRepository : JpaSpecificationRepository<Announcement, UUID> {

    @org.springframework.data.jpa.repository.EntityGraph("announcement-graph")
    @Query("select t from Announcement t where t.subjectExecution.id = :id")
    fun findAllBySubjectExecutionId(id: UUID): List<AnnouncementViewDTO>
}
