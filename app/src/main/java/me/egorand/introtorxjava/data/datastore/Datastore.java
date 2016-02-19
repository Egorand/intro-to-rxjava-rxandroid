package me.egorand.introtorxjava.data.datastore;

import me.egorand.introtorxjava.data.entities.Data;
import rx.Observable;

/**
 * @author Egor
 */
public interface Datastore<T> {

    Observable<Data<T>> loadAll();

    void add(T item);

    void clear();
}
