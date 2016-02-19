package me.egorand.introtorxjava.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.entities.Topic;

public abstract class TopicDetailFragment extends RxFragment {

    public static final String ARG_TOPIC_NAME = "topic_name";

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment fragment = fragmentForTopic(topic);
        Bundle args = new Bundle();
        args.putString(ARG_TOPIC_NAME, topic.name());
        fragment.setArguments(args);
        return fragment;
    }

    private static TopicDetailFragment fragmentForTopic(Topic topic) {
        switch (topic) {
            case CREATING_OBSERVABLES:
                return new CreatingObservablesFragment();
            case DEBUGGING:
                return new DebuggingFragment();
            case RETROFIT:
                return new RetrofitFragment();
            default:
                throw new IllegalArgumentException("Unknown topic: " + topic);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Topic topic = Topic.valueOf(getArguments().getString(ARG_TOPIC_NAME));
        Activity activity = this.getActivity();
        Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.detail_toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getString(topic.titleRes));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topic_detail, container, false);
    }
}
