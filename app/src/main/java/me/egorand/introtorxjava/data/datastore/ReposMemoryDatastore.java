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

package me.egorand.introtorxjava.data.datastore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.egorand.introtorxjava.data.entities.Data;
import me.egorand.introtorxjava.data.entities.Repo;
import rx.Observable;

/**
 * @author Egor
 */
public class ReposMemoryDatastore implements Datastore<List<Repo>> {

    private final List<Repo> datastore;

    public ReposMemoryDatastore() {
        this.datastore = new ArrayList<>();
    }

    @Override
    public Observable<Data<List<Repo>>> loadAll() {
        return !datastore.isEmpty() ?
                Observable.just(Data.memory(Collections.unmodifiableList(datastore))) :
                Observable.empty();
    }

    @Override
    public void add(List<Repo> items) {
        datastore.addAll(items);
    }

    @Override
    public void clear() {
        datastore.clear();
    }
}
