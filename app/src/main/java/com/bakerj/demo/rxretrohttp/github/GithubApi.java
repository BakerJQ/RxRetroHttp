package com.bakerj.demo.rxretrohttp.github;

import com.bakerj.demo.rxretrohttp.entity.github.GithubUser;
import com.bakerj.rxretrohttp.RxRetroHttp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GithubApi {
    @Headers({RxRetroHttp.MOCK_DATA_KEY+":{\"login\":\"BakerJQ\",\"id\":11451739," +
            "\"node_id\":\"MDQ6VXNlcjExNDUxNzM5\",\"avatar_url\":\"https://avatars1" +
            ".githubusercontent.com/u/11451739?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api" +
            ".github.com/users/BakerJQ\",\"html_url\":\"https://github.com/BakerJQ\"," +
            "\"followers_url\":\"https://api.github.com/users/BakerJQ/followers\"," +
            "\"following_url\":\"https://api.github.com/users/BakerJQ/following{/other_user}\"," +
            "\"gists_url\":\"https://api.github.com/users/BakerJQ/gists{/gist_id}\"," +
            "\"starred_url\":\"https://api.github.com/users/BakerJQ/starred{/owner}{/repo}\"," +
            "\"subscriptions_url\":\"https://api.github.com/users/BakerJQ/subscriptions\"," +
            "\"organizations_url\":\"https://api.github.com/users/BakerJQ/orgs\"," +
            "\"repos_url\":\"https://api.github.com/users/BakerJQ/repos\"," +
            "\"events_url\":\"https://api.github.com/users/BakerJQ/events{/privacy}\"," +
            "\"received_events_url\":\"https://api.github.com/users/BakerJQ/received_events\"," +
            "\"type\":\"User\",\"site_admin\":false,\"name\":\"BakerJ\",\"company\":null," +
            "\"blog\":\"http://bakerjq.com\",\"location\":\"Nanchang, China\",\"email\":null," +
            "\"hireable\":null,\"bio\":\"Only cowards don't stand up to a challenge\"," +
            "\"twitter_username\":null,\"public_repos\":11,\"public_gists\":0,\"followers\":70," +
            "\"following\":1,\"created_at\":\"2015-03-13T03:01:33Z\"," +
            "\"updated_at\":\"2020-05-14T07:02:40Z\"}",
    RxRetroHttp.MOCK_DELAY_KEY+":500"})
    @GET("users/{userName}")
    Observable<GithubUser> getUser(@Path("userName") String userName);
}
