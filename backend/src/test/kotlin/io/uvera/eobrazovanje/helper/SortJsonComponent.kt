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

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.node.ArrayNode
import org.springframework.data.domain.Sort
import java.io.IOException

/**
 * This class provides provides support for serializing and deserializing for Spring
 * [Sort] object.
 *
 * @author Can Bezmen
 */
class SortJsonComponent {
    class SortSerializer : JsonSerializer<Sort>() {
        @Throws(IOException::class)
        override fun serialize(value: Sort, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeStartArray()
            value.iterator().forEachRemaining { v: Sort.Order ->
                try {
                    gen.writeObject(v)
                } catch (e: IOException) {
                    throw Exception("Couldn't serialize object $v")
                }
            }
            gen.writeEndArray()
        }

        override fun handledType(): Class<Sort> {
            return Sort::class.java
        }
    }

    class SortDeserializer : JsonDeserializer<Sort?>() {
        @Throws(IOException::class)
        override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): Sort? {
            val treeNode = jsonParser.codec.readTree<TreeNode>(jsonParser)
            if (treeNode.isArray) {
                val arrayNode = treeNode as ArrayNode
                val orders: MutableList<Sort.Order> = ArrayList()
                for (jsonNode in arrayNode) {
                    val order = Sort.Order(Sort.Direction.valueOf(
                        jsonNode["direction"].textValue()),
                        jsonNode["property"].textValue())
                    orders.add(order)
                }
                return Sort.by(orders)
            }
            return null
        }

        override fun handledType(): Class<Sort> {
            return Sort::class.java
        }
    }
}
