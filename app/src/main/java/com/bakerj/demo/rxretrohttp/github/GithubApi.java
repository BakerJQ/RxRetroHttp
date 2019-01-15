package com.bakerj.demo.rxretrohttp.github;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{userName}")
    Observable<GithubUser> getUser(@Path("userName") String userName);
}
