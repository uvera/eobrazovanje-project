package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "active", nullable = false)
    var active: Boolean = true,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: RoleEnum,

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    var announcementComments: MutableList<AnnouncementComment> = mutableListOf(),
) : BaseEntity()
@Repository
interface UserRepository : JpaSpecificationRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun deleteAllByEmailNotIn(email: MutableCollection<String>)
}
