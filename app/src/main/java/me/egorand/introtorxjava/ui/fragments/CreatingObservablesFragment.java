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

package me.egorand.introtorxjava.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import me.egorand.introtorxjava.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class CreatingObservablesFragment extends TopicDetailFragment {

    private TextView consoleOnCreate;
    private TextView consoleJust;
    private TextView consoleFrom;

    private CompositeSubscription subscription = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_creating_observables, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        consoleOnCreate = (TextView) view.findViewById(R.id.console_oncreate);
        consoleJust = (TextView) view.findViewById(R.id.console_just);
        consoleFrom = (TextView) view.findViewById(R.id.console_from);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireOnCreateObservable();
        fireJustObservable();
        fireFromObservable();
    }

    private void fireOnCreateObservable() {
        Observable<Integer> observable = Observable.create(subscriber -> {
            try {
                for (int value = 1; value <= 3; value++) {
                    subscriber.onNext(value);
                }
                subscriber.onCompleted();
            } catch (Throwable t) {
                subscriber.onError(t);
            }
        });
        subscribeConsole(observable, consoleOnCreate);
    }

    private void fireJustObservable() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        subscribeConsole(observable, consoleJust);
    }

    private void fireFromObservable() {
        Observable<Integer> observable = Observable.from(Arrays.asList(1, 2, 3));
        Observable.from(Arrays.asList(1, 2, 3));
        Observable.from(new Integer[]{1, 2, 3});
        subscribeConsole(observable, consoleFrom);
    }

    private void subscribeConsole(Observable<Integer> observable, TextView console) {
        subscription.add(observable
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> console.append(value + "\n"),
                        error -> console.setText("Error: " + error.getLocalizedMessage()),
                        () -> console.append("Done!")));
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }
}
