package me.egorand.introtorxjava.data.entities;

import android.support.annotation.StringRes;

import me.egorand.introtorxjava.R;

public enum Topic {

    CREATING_OBSERVABLES(R.string.topic_creating_observables),
    DEBUGGING(R.string.topic_debugging),
    RETROFIT(R.string.topic_retrofit);

    public final @StringRes int titleRes;

    Topic(int titleRes) {
        this.titleRes = titleRes;
    }
}
