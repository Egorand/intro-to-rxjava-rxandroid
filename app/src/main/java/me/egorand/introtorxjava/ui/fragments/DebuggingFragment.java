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

import me.egorand.introtorxjava.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Egor
 */
public class DebuggingFragment extends TopicDetailFragment {

    private TextView console;

    private Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_debugging, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        console = (TextView) view.findViewById(R.id.console);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireDebuggableObservable();
    }

    private void fireDebuggableObservable() {
        subscription = Observable.just(1, 2, 3)
                .compose(bindToLifecycle())
                .doOnNext(next -> append("Before filter: " + next))
                .filter(value -> value % 2 == 0)
                .doOnNext(next -> append("After filter: " + next))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> append(String.valueOf(value)),
                        error -> append("Error!"),
                        () -> append("Completed!"));
    }

    private void append(String text) {
        console.append(text + "\n");
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
