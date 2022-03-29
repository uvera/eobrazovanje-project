package io.uvera.eobrazovanje.common.repository.user

import io.uvera.eobrazovanje.common.repository.BaseEntity
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "active", nullable = false)
    var active: Boolean = true,

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "owner_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var roles: MutableList<RoleEnum> = mutableListOf()
): BaseEntity()


@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String) : User?
}
