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
