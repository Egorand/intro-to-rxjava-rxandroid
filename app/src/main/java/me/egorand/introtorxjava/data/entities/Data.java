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
