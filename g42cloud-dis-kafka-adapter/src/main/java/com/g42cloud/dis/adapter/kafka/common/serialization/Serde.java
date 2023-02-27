/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.g42cloud.dis.adapter.kafka.common.serialization;

import java.io.Closeable;
import java.util.Map;

/**
 * The interface for wrapping a serializer and deserializer for the given data type.
 *
 * @param <T> Type to be serialized from and deserialized into.
 *
 * A class that implements this interface is expected to have a constructor with no parameter.
 */
public interface Serde<T> extends Closeable {

    /**
     * Configure this class, which will configure the underlying serializer and deserializer.
     *
     * @param configs configs in key/value pairs
     * @param isKey whether is for key or value
     */
    void configure(Map<String, ?> configs, boolean isKey);

    /**
     * Close this serde class, which will close the underlying serializer and deserializer.
     * This method has to be idempotent because it might be called multiple times.
     */
    @Override
    void close();

    Serializer<T> serializer();

    Deserializer<T> deserializer();
}
