package io.uvera.eobrazovanje.util.extensions

import io.uvera.eobrazovanje.common.repository.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

operator fun <T, ID, K : Any?> JpaSpecificationRepository<T, ID>.invoke(block: JpaSpecificationRepository<T, ID>.() -> K) =
    with(this, block)


context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> T.update(selfUpdateBlock: T.() -> Unit): T {
    selfUpdateBlock(this@update)
    return this@JpaRepository.save(this@update)
}

context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> T.save(): T {
    return this@JpaRepository.save(this@save)
}


context(JpaRepository<T, ID>)
fun <ID, T : BaseEntity> Collection<T>.saveAll(): List<T> {
    return this@JpaRepository.saveAll(this@saveAll)
}

@NoRepositoryBean
interface JpaSpecificationRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>
