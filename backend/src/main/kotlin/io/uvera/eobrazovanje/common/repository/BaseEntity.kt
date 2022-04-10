package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.error.exception.EntityStateException
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Version
    open val version: Long? = null

    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @ColumnDefault("random_uuid()")
    private lateinit var _id: UUID
    val id: UUID
        get() = if (this::_id.isInitialized) _id else throw IDUninitializedException()

    override fun equals(other: Any?) = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        id != (other as BaseEntity).id -> false
        else -> true
    }

    override fun hashCode(): Int = id.hashCode()
}

class IDUninitializedException : EntityStateException("ID not initialized")
