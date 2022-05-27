package io.uvera.eobrazovanje.util.extensions

import io.uvera.eobrazovanje.common.repository.BaseEntity
import io.uvera.eobrazovanje.error.dto.ObjectErrorCompact
import io.uvera.eobrazovanje.error.exception.EntityNotFoundException
import io.uvera.eobrazovanje.error.exception.EntityStateException
import io.uvera.eobrazovanje.util.AnyResponseEntity
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import java.util.*

/*
 * BindingResult's ObjectError in a compact form
 */
val List<ObjectError>.compact: List<ObjectErrorCompact>
    get() = this.map {
        ObjectErrorCompact(it.defaultMessage ?: "Unknown error", it.code ?: "UnknownException")
    }

/*
 * Create response entity with empty json response
 */
object EmptyObject

fun ResponseEntity.BodyBuilder.empty(): ResponseEntity<Any> {
    return this.body(EmptyObject)
}

val <T : Any> T.ok: ResponseEntity<T>
    get() = ResponseEntity.ok(this)

val emptyOk: AnyResponseEntity
    get() = ResponseEntity.ok(EmptyObject)

inline fun <reified T : BaseEntity> notFoundById(id: UUID): Nothing {
    throw EntityNotFoundException("${T::class.simpleName}: not found by id: $id")
}

inline fun <reified T : BaseEntity> notFoundByEmail(email: String): Nothing {
    throw EntityNotFoundException("${T::class.simpleName}: not found by email: $email")
}

inline fun <reified T : BaseEntity> notFoundByCodeName(code: String): Nothing {
    throw EntityNotFoundException("${T::class.simpleName}: not found by code: $code")
}

fun entityStateError(message: String): Nothing {
    throw EntityStateException(message)
}
