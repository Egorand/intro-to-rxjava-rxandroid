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
