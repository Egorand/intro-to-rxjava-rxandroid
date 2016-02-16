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
