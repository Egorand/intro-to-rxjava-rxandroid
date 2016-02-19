package me.egorand.introtorxjava.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

import me.egorand.introtorxjava.data.datastore.ReposDiskDatastore;
import me.egorand.introtorxjava.data.datastore.ReposMemoryDatastore;
import me.egorand.introtorxjava.data.entities.Data;
import me.egorand.introtorxjava.data.entities.Repo;
import me.egorand.introtorxjava.data.loaders.ReposLoader;
import me.egorand.introtorxjava.data.rest.GithubApiClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author Egor
 */
public class ReposLoaderFragment extends Fragment {

    @SuppressWarnings("unused")
    public static final String TAG = ReposLoaderFragment.class.getSimpleName();

    private ReposLoader reposLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        this.reposLoader = new ReposLoader(
                new ReposMemoryDatastore(),
                new ReposDiskDatastore(),
                createGithubApiClient());
    }

    private GithubApiClient createGithubApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(GithubApiClient.class);
    }

    public Observable<Data<List<Repo>>> loadRepos() {
        return reposLoader.loadRepos();
    }

    public Observable<Data<List<Repo>>> reloadRepos() {
        return reposLoader.reloadRepos();
    }
}
