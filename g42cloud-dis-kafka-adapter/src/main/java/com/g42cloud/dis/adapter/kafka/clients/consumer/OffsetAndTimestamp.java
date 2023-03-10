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
package com.g42cloud.dis.adapter.kafka.clients.consumer;

import com.g42cloud.dis.adapter.kafka.common.utils.Utils;

/**
 * A container class for offset and timestamp.
 */
public final class OffsetAndTimestamp {
    private final long timestamp;
    private final long offset;

    public OffsetAndTimestamp(long offset, long timestamp) {
        this.offset = offset;
        assert this.offset >= 0;
        this.timestamp = timestamp;
        assert this.timestamp >= 0;
    }

    public long timestamp() {
        return timestamp;
    }

    public long offset() {
        return offset;
    }

    @Override
    public String toString() {
        return "(timestamp=" + timestamp + ", offset=" + offset + ")";
    }

    @Override
    public int hashCode() {
        return 31 * Utils.longHashcode(timestamp) + Utils.longHashcode(offset);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof OffsetAndTimestamp))
            return false;
        OffsetAndTimestamp other = (OffsetAndTimestamp) o;
        return this.timestamp == other.timestamp() && this.offset == other.offset();
    }
}
