package io.uvera.eobrazovanje.util.extensions

import io.uvera.eobrazovanje.error.dto.ObjectErrorCompact
import liquibase.pro.packaged.T
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError

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
