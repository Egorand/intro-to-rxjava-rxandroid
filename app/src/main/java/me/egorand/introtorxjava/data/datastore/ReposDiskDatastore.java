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
            return !repos.isEmpty() ? Observable.just(Data.disk(repos)) : Observable.empty();
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
