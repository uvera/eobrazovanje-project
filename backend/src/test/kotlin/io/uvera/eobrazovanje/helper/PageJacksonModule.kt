/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.uvera.eobrazovanje.helper

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.uvera.eobrazovanje.helper.PageJacksonModule.PageMixIn
import io.uvera.eobrazovanje.helper.PageJacksonModule.SimplePageImpl
import org.springframework.data.domain.*
import java.util.function.Function

/**
 * This Jackson module provides support to deserialize Spring [Page] objects.
 *
 * @author Pascal Büttiker
 * @author Olga Maciaszek-Sharma
 * @author Pedro Mendes
 * @author Nikita Konev
 */
class PageJacksonModule : Module() {
    override fun getModuleName(): String {
        return "PageJacksonModule"
    }

    override fun version(): Version {
        return Version(0, 1, 0, "", null, null)
    }

    override fun setupModule(context: SetupContext) {
        context.setMixInAnnotations(Page::class.java, PageMixIn::class.java)
    }

    @JsonDeserialize(`as` = SimplePageImpl::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private interface PageMixIn
    internal class SimplePageImpl<T>(
        @JsonProperty("content") content: List<T>?,
        @JsonProperty("number") number: Int,
        @JsonProperty("size") size: Int,
        @JsonProperty("totalElements") @JsonAlias(
            "total-elements",
            "total_elements",
            "totalelements",
            "TotalElements"
        ) totalElements: Long,
        @JsonProperty("sort") sort: Sort?
    ) : Page<T> {
        private var delegate: Page<T>? = null

        init {
            delegate = if (size > 0) {
                val pageRequest: PageRequest = if (sort != null) {
                    PageRequest.of(number, size, sort)
                } else {
                    PageRequest.of(number, size)
                }
                PageImpl(content!!.toMutableList(), pageRequest, totalElements)
            } else {
                PageImpl(content!!.toMutableList())
            }
        }

        @JsonProperty
        override fun getTotalPages(): Int {
            return delegate!!.totalPages
        }

        @JsonProperty
        override fun getTotalElements(): Long {
            return delegate!!.totalElements
        }

        @JsonProperty
        override fun getNumber(): Int {
            return delegate!!.number
        }

        @JsonProperty
        override fun getSize(): Int {
            return delegate!!.size
        }

        @JsonProperty
        override fun getNumberOfElements(): Int {
            return delegate!!.numberOfElements
        }

        @JsonProperty
        override fun getContent(): List<T> {
            return delegate!!.content
        }

        @JsonProperty
        override fun hasContent(): Boolean {
            return delegate!!.hasContent()
        }

        @JsonIgnore
        override fun getSort(): Sort {
            return delegate!!.sort
        }

        @JsonProperty
        override fun isFirst(): Boolean {
            return delegate!!.isFirst
        }

        @JsonProperty
        override fun isLast(): Boolean {
            return delegate!!.isLast
        }

        @JsonIgnore
        override fun hasNext(): Boolean {
            return delegate!!.hasNext()
        }

        @JsonIgnore
        override fun hasPrevious(): Boolean {
            return delegate!!.hasPrevious()
        }

        @JsonIgnore
        override fun nextPageable(): Pageable {
            return delegate!!.nextPageable()
        }

        @JsonIgnore
        override fun previousPageable(): Pageable {
            return delegate!!.previousPageable()
        }

        @JsonIgnore
        override fun <S> map(converter: Function<in T, out S>): Page<S> {
            return delegate!!.map(converter)
        }

        @JsonIgnore
        override fun iterator(): MutableIterator<T> {
            return delegate!!.iterator()
        }

        @JsonIgnore
        override fun getPageable(): Pageable {
            return delegate!!.pageable
        }

        @JsonIgnore
        override fun isEmpty(): Boolean {
            return delegate!!.isEmpty
        }

        override fun hashCode(): Int {
            return delegate.hashCode()
        }

        override fun equals(obj: Any?): Boolean {
            return delegate == obj
        }

        override fun toString(): String {
            return delegate.toString()
        }
    }
}
