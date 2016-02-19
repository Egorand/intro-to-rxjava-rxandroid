package me.egorand.introtorxjava.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.egorand.introtorxjava.R;
import me.egorand.introtorxjava.ui.adapters.ReposAdapter;
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

    private ReposLoaderFragment loaderFragment;

    private Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.loaderFragment =
                (ReposLoaderFragment) getFragmentManager().findFragmentByTag(ReposLoaderFragment.TAG);
    }

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

        loadRepos();
    }

    private void loadRepos() {
        subscription = loaderFragment.loadRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(data -> Toast.makeText(getActivity(), data.source.name(), Toast.LENGTH_SHORT).show())
                .doOnTerminate(() -> progress.setVisibility(View.GONE))
                .subscribe(
                        data -> reposAdapter.setRepos(data.data),
                        error -> Log.e(LOGTAG, error.getLocalizedMessage(), error));
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
