package io.uvera.eobrazovanje.common.repository

import javax.persistence.*

@Entity
@Table(name = "announcement_comment")
class AnnouncementComment(
    @ManyToOne(optional = false) @JoinColumn(
        name = "announcement_id",
        nullable = false
    ) var announcement: Announcement,

    @Lob
    @Column(name = "text", nullable = false)
    var text: String,

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    var parentComment: AnnouncementComment? = null,

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    var announcementComments: MutableList<AnnouncementComment> = mutableListOf(),

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
) : BaseEntity()
