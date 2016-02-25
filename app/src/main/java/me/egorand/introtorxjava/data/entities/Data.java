/*
 * Copyright 2016 Egor Andreevici
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

package me.egorand.introtorxjava.data.entities;

/**
 * @author Egor
 */
public class Data<T> {

    public enum Source {
        NETWORK, DISK, MEMORY
    }

    public final T data;
    public final Source source;

    public static <T> Data<T> network(T data) {
        return new Data<>(data, Source.NETWORK);
    }

    public static <T> Data<T> disk(T data) {
        return new Data<>(data, Source.DISK);
    }

    public static <T> Data<T> memory(T data) {
        return new Data<>(data, Source.MEMORY);
    }

    Data(T data, Source source) {
        this.data = data;
        this.source = source;
    }
}
