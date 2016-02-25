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

package me.egorand.introtorxjava.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.events.TopicSelectedEvent;
import me.egorand.introtorxjava.ui.adapters.TopicsAdapter;
import me.egorand.introtorxjava.ui.fragments.TopicDetailFragment;

public class TopicListActivity extends AppCompatActivity {

    private boolean inTwoPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.topic_list);
        recyclerView.setAdapter(new TopicsAdapter());

        if (findViewById(R.id.topic_detail_container) != null) {
            inTwoPaneMode = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onTopicSelected(TopicSelectedEvent event) {
        if (inTwoPaneMode) {
            TopicDetailFragment fragment = TopicDetailFragment.newInstance(event.topic);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.topic_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = TopicDetailActivity.withTopic(this, event.topic);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
