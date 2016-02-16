package me.egorand.introtorxjava.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;

import me.egorand.introtorxjava.data.Topic;
import me.egorand.introtorxjava.events.TopicSelectedEvent;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(android.R.layout.simple_list_item_checked, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Topic topic = Topic.values()[position];
        holder.topicTitle.setText(topic.titleRes);
        RxView.clicks(holder.itemView)
                .subscribe(v -> {
                    holder.topicTitle.setChecked(true);
                    EventBus.getDefault().post(new TopicSelectedEvent(topic));
                });
    }

    @Override
    public int getItemCount() {
        return Topic.values().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckedTextView topicTitle;

        public ViewHolder(View view) {
            super(view);
            topicTitle = (CheckedTextView) view.findViewById(android.R.id.text1);
        }
    }
}
