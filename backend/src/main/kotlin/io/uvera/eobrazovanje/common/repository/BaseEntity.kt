package io.uvera.eobrazovanje.common.repository

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Version
    open val version: Long? = null

    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false, updatable = false)
    open val id: UUID = UUID.randomUUID()

    override fun equals(other: Any?) = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        id != (other as BaseEntity).id -> false
        else -> true
    }

    override fun hashCode(): Int = id.hashCode()
}
