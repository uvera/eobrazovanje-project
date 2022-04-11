package io.uvera.eobrazovanje

import org.springframework.http.ResponseEntity

fun <T> ResponseEntity<T>.resolve(): Pair<T, ResponseEntity<T>> = Pair(this.body!!, this)
