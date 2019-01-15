package com.bakerj.demo.rxretrohttp.github;

import com.bakerj.demo.rxretrohttp.entity.github.GithubUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{userName}")
    Observable<GithubUser> getUser(@Path("userName") String userName);
}
