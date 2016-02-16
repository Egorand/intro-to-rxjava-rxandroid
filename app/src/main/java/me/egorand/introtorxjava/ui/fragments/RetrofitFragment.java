package me.egorand.introtorxjava.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.rest.GithubApiClient;
import me.egorand.introtorxjava.ui.adapters.ReposAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Egor
 */
public class RetrofitFragment extends TopicDetailFragment {

    private static final String LOGTAG = RetrofitFragment.class.getSimpleName();

    private View progress;

    private ReposAdapter reposAdapter;

    private Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_retrofit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initReposView(view);
        progress = view.findViewById(android.R.id.progress);
    }

    private void initReposView(View rootView) {
        RecyclerView reposView = (RecyclerView) rootView.findViewById(R.id.repos_list);
        reposView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reposAdapter = new ReposAdapter(LayoutInflater.from(getActivity()));
        reposView.setAdapter(reposAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GithubApiClient githubApiClient = initGithubApiClient();
        loadRepos(githubApiClient);
    }

    private GithubApiClient initGithubApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(GithubApiClient.class);
    }

    private void loadRepos(GithubApiClient githubApiClient) {
        subscription = githubApiClient.getUserRepos("EgorAnd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        repos -> {
                            progress.setVisibility(View.GONE);
                            reposAdapter.setRepos(repos);
                        },
                        error -> {
                            progress.setVisibility(View.GONE);
                            Log.e(LOGTAG, error.getLocalizedMessage(), error);
                        });
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
