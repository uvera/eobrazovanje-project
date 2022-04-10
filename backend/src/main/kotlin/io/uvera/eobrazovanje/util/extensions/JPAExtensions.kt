@file:Suppress("UNCHECKED_CAST")

package io.uvera.eobrazovanje.util.extensions

import io.uvera.eobrazovanje.common.repository.BaseEntity
import io.uvera.eobrazovanje.error.exception.EntityNotFoundException
import liquibase.pro.packaged.T
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.findByIdOrNull
import java.util.*

operator fun <T, ID, K : Any?, X : JpaSpecificationRepository<T, ID>> X.invoke(block: X.() -> K) =
    with(this, block)


context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> T.update(selfUpdateBlock: T.() -> Unit): T {
    selfUpdateBlock(this@update)
    return this@JpaRepository.save(this@update)
}

context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> Collection<T>.updateEach(selfUpdateBlock: T.() -> Unit): MutableList<T> {
    this@updateEach.forEach { it.selfUpdateBlock() }
    return this@JpaRepository.saveAll(this@updateEach)
}

fun <T : BaseEntity> T.tap(selfTapBlock: T.() -> Unit): T {
    selfTapBlock(this)
    return this
}

context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> T.save(): T {
    return this@JpaRepository.save(this@save)
}


context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> Collection<T>.saveAll(): List<T> {
    return this@JpaRepository.saveAll(this@saveAll)
}

context(JpaRepository<T, UUID>)
        inline val <reified T : BaseEntity> T.reload: T
    get() = this@JpaRepository.findByIdOrNull(this@reload.id)
        ?: throw EntityNotFoundException("${T::class.simpleName}: not found by id: ${this@reload.id}")

@NoRepositoryBean
interface JpaSpecificationRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>
