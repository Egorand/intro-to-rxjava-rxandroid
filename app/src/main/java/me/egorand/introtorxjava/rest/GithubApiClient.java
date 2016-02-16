package me.egorand.introtorxjava.rest;

import java.util.List;

import me.egorand.introtorxjava.data.Repo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Egor
 */
public interface GithubApiClient {

    String BASE_URL = "https://api.github.com";

    @GET("/users/{username}/repos") Observable<List<Repo>> getUserRepos(@Path("username") String username);
}
