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

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.egorand.introtorxjava.data.entities.Data;
import me.egorand.introtorxjava.data.entities.Repo;
import rx.Observable;

/**
 * @author Egor
 */
public class ReposDiskDatastore implements Datastore<List<Repo>> {

    @Override
    public Observable<Data<List<Repo>>> loadAll() {
        return Observable.defer(() -> {
            List<Repo> repos = SQLite.select().from(Repo.class).queryList();
            return !repos.isEmpty() ?
                    Observable.just(Data.disk(repos)) :
                    Observable.empty();
        });
    }

    @Override
    public void add(List<Repo> items) {
        for (Repo repo : items) {
            repo.insert();
        }
    }

    @Override
    public void clear() {
        Delete.table(Repo.class);
    }
}
