package me.egorand.introtorxjava.events;

import me.egorand.introtorxjava.data.entities.Topic;

public class TopicSelectedEvent {

    public final Topic topic;

    public TopicSelectedEvent(Topic topic) {
        this.topic = topic;
    }
}
