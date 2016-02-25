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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.entities.Topic;
import me.egorand.introtorxjava.ui.fragments.ReposLoaderFragment;
import me.egorand.introtorxjava.ui.fragments.TopicDetailFragment;

public class TopicDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            attachLoaderFragment();

            Topic topic = Topic.valueOf(getIntent().getStringExtra(TopicDetailFragment.ARG_TOPIC_NAME));
            TopicDetailFragment fragment = TopicDetailFragment.newInstance(topic);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.topic_detail_container, fragment)
                    .commit();
        }
    }

    private void attachLoaderFragment() {
        ReposLoaderFragment loaderFragment = new ReposLoaderFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(loaderFragment, ReposLoaderFragment.TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigateUpTo(new Intent(this, TopicListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent withTopic(Context context, Topic topic) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra(TopicDetailFragment.ARG_TOPIC_NAME, topic.name());
        return intent;
    }
}
