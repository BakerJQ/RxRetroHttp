package com.bakerj.demo.rxretrohttp.gank;

import com.bakerj.demo.rxretrohttp.entity.gank.GankGirl;
import com.bakerj.rxretrohttp.annotation.RetroTag;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

@RetroTag(tag = "Gank")
public interface GankApi {
    @GET("福利/10/1")
    Observable<List<GankGirl>> getGankGirls();
}
