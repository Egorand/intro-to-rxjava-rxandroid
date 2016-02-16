package me.egorand.introtorxjava.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.Topic;

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
            default:
                throw new IllegalArgumentException("Unknown topic: " + topic);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Topic topic = Topic.valueOf(getArguments().getString(ARG_TOPIC_NAME));
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getString(topic.titleRes));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topic_detail, container, false);
    }
}
