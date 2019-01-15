package com.bakerj.demo.rxretrohttp.gank;

import com.bakerj.demo.rxretrohttp.entity.gank.GankGirl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GankApi {
    @GET("福利/10/1")
    Observable<List<GankGirl>> getGankGirls();
}
