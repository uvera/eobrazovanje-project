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

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleSerializers
import org.springframework.data.domain.Sort

/**
 * This Jackson module provides support for serializing and deserializing for Spring
 * [Sort] object.
 *
 * @author Can Bezmen
 */
class SortJacksonModule : Module() {
    override fun getModuleName(): String {
        return "SortModule"
    }

    override fun version(): Version {
        return Version(0, 1, 0, "", null, null)
    }

    override fun setupModule(context: SetupContext) {
        val serializers = SimpleSerializers()
        serializers.addSerializer(Sort::class.java, SortJsonComponent.SortSerializer())
        context.addSerializers(serializers)
        val deserializers = SimpleDeserializers()
        deserializers.addDeserializer(Sort::class.java, SortJsonComponent.SortDeserializer())
        context.addDeserializers(deserializers)
    }
}
