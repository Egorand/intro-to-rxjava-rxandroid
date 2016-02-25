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

package me.egorand.introtorxjava.data.loaders;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.egorand.introtorxjava.data.datastore.Datastore;
import me.egorand.introtorxjava.data.entities.Data;
import me.egorand.introtorxjava.data.entities.Repo;
import me.egorand.introtorxjava.data.rest.GithubApiClient;
import rx.Observable;

/**
 * @author Egor
 */
public class ReposLoader {

    private final Datastore<List<Repo>> memoryDatastore;
    private final Datastore<List<Repo>> diskDatastore;
    private final GithubApiClient githubApiClient;

    public ReposLoader(Datastore<List<Repo>> memoryDatastore,
                       Datastore<List<Repo>> diskDatastore,
                       GithubApiClient githubApiClient) {
        this.memoryDatastore = memoryDatastore;
        this.diskDatastore = diskDatastore;
        this.githubApiClient = githubApiClient;
    }

    public Observable<Data<List<Repo>>> loadRepos() {
        return Observable
                .concat(memory(), disk(), network())
                .first();
    }

    public Observable<Data<List<Repo>>> reloadRepos() {
        return network();
    }

    private Observable<Data<List<Repo>>> memory() {
        return memoryDatastore.loadAll();
    }

    private Observable<Data<List<Repo>>> disk() {
        return diskDatastore.loadAll()
                .doOnNext(data -> {
                    memoryDatastore.clear();
                    memoryDatastore.add(data.data);
                });
    }

    private Observable<Data<List<Repo>>> network() {
        return githubApiClient.getUserRepos("EgorAnd")
                .delay(5, TimeUnit.SECONDS)
                .doOnNext(items -> {
                    memoryDatastore.clear();
                    memoryDatastore.add(items);
                    diskDatastore.clear();
                    diskDatastore.add(items);
                })
                .map(items -> Data.network(items));
    }

    public void clearCache() {
        memoryDatastore.clear();
        diskDatastore.clear();
    }
}
