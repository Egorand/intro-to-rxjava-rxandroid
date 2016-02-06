package me.egorand.introtorxjava.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.Topic;

public class TopicDetailFragment extends Fragment {

    public static final String ARG_TOPIC_NAME = "topic_name";

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOPIC_NAME, topic.name());
        fragment.setArguments(args);
        return fragment;
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
