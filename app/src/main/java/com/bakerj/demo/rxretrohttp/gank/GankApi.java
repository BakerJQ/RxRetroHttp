package com.bakerj.demo.rxretrohttp.gank;

import com.bakerj.demo.rxretrohttp.entity.gank.GankGirl;
import com.bakerj.rxretrohttp.annotation.RetroTag;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

@RetroTag(tag = "Gank")
public interface GankApi {
    @GET("category/Girl/type/Girl/page/1/count/10")
    Observable<List<GankGirl>> getGankGirls();
}
