package me.egorand.introtorxjava;

import org.junit.Test;

import rx.Observable;
import rx.observables.BlockingObservable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Egor
 */
public class ObservableTests {

    @Test
    public void testingWithBlockingObservable() {
        Observable<Integer> intObservable = Observable.just(1, 2, 3);
        BlockingObservable<Integer> blocking = intObservable.toBlocking();

        assertThat(blocking.first()).isEqualTo(1);
        assertThat(blocking.last()).isEqualTo(3);

        blocking.getIterator();
        blocking.toIterable();
    }

    @Test
    public void testingWithTestSubscriber() {
        Observable<Integer> intObservable = Observable.just(1, 2, 3);
        TestSubscriber<Integer> testSubscriber = TestSubscriber.create();

        intObservable.subscribe(testSubscriber);

        testSubscriber.assertValues(1, 2, 3);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }
}
