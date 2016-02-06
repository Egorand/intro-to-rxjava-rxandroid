package me.egorand.introtorxjava.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.Topic;
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
            Topic topic = Topic.valueOf(getIntent().getStringExtra(TopicDetailFragment.ARG_TOPIC_NAME));
            TopicDetailFragment fragment = TopicDetailFragment.newInstance(topic);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.topic_detail_container, fragment)
                    .commit();
        }
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
