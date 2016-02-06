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

public class CreatingObservablesFragment extends TopicDetailFragment {

    private TextView consoleOnCreate;
    private TextView consoleJust;
    private TextView consoleFrom;

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
        subscribeConsole(observable, consoleFrom);
    }

    private void subscribeConsole(Observable<Integer> observable, TextView console) {
        observable
                .compose(bindToLifecycle())
                .subscribe(
                        value -> console.append(value + "\n"),
                        error -> console.setText("Error: " + error.getLocalizedMessage()),
                        () -> console.append("Done!"));
    }
}
