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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.data.entities.Data;
import me.egorand.introtorxjava.data.entities.Repo;
import me.egorand.introtorxjava.ui.adapters.ReposAdapter;
import rx.Observable;
import rx.Subscription;

/**
 * @author Egor
 */
public class RetrofitFragment extends TopicDetailFragment {

    private static final String LOGTAG = RetrofitFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefresh;

    private ReposAdapter reposAdapter;

    private ReposLoaderFragment loaderFragment;

    private Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        this.loaderFragment =
                (ReposLoaderFragment) getFragmentManager().findFragmentByTag(ReposLoaderFragment.TAG);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_retrofit_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_cache) {
            loaderFragment.clearCache();
            Toast.makeText(getActivity(), "Cache cleared", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_retrofit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initReposView(view);
        initSwipeRefreshLayout(view);
    }

    private void initReposView(View rootView) {
        RecyclerView reposView = (RecyclerView) rootView.findViewById(R.id.repos_list);
        reposView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reposAdapter = new ReposAdapter(LayoutInflater.from(getActivity()));
        reposView.setAdapter(reposAdapter);
    }

    private void initSwipeRefreshLayout(View rootView) {
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(() -> reloadRepos());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadRepos();
    }

    private void loadRepos() {
        unsubscribeFromPrevious();
        subscribeToObservable(loaderFragment.loadRepos());
    }

    private void unsubscribeFromPrevious() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void subscribeToObservable(Observable<Data<List<Repo>>> observable) {
        subscription = observable
                .doOnSubscribe(() -> setRefreshing(true))
                .doOnNext(data -> Toast.makeText(getActivity(), data.source.name(), Toast.LENGTH_SHORT).show())
                .doOnTerminate(() -> setRefreshing(false))
                .subscribe(
                        data -> reposAdapter.setRepos(data.data),
                        error -> Log.e(LOGTAG, error.getLocalizedMessage(), error));
    }

    private void setRefreshing(boolean refreshing) {
        swipeRefresh.post(() -> swipeRefresh.setRefreshing(refreshing));
    }

    private void reloadRepos() {
        unsubscribeFromPrevious();
        subscribeToObservable(loaderFragment.reloadRepos());
    }

    @Override
    public void onDestroy() {
        unsubscribeFromPrevious();
        super.onDestroy();
    }
}
