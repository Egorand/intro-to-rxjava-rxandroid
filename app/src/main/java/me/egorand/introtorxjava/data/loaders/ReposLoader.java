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
}
