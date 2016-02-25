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
