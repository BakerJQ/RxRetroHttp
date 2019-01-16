package com.bakerj.demo.rxretrohttp;

import android.app.Application;

import com.bakerj.demo.rxretrohttp.entity.gank.GankApiResult;
import com.bakerj.demo.rxretrohttp.entity.github.GithubApiResult;
import com.bakerj.demo.rxretrohttp.entity.weather.WeatherApiResult;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.client.SimpleRetroClient;
import com.blankj.utilcode.util.Utils;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        RxRetroHttp.init(this)
                .setBaseUrl("https://api.github.com/")//your main url
                .setDefaultErrMsg("Github开小差了")//default error hint message
                .setApiResultClass(GithubApiResult.class)//your main api result structure, if not, will use default gson converter
                .generateRetroClient()

                .setApiResultClass(GankApiResult.class)//other result
                .setBaseUrl("http://gank.io/api/data/")//other url
                .setDefaultErrMsg("Gank开小差了")
                .generateRetroClient("Gank")

                .setApiResultClass(WeatherApiResult.class)
                .setBaseUrl("https://www.sojson.com/open/api/weather/")
                .setDefaultErrMsg("Weather开小差了")
                .addClient(new SimpleRetroClient(), "Weather");//other request tag;
//        gankInit();
//        weatherInit();
    }

    private void gankInit() {
        RxRetroHttp.getInstance()
                .setApiResultClass(GankApiResult.class)//other result
                .setBaseUrl("http://gank.io/api/data/")//other url
                .generateRetroClient("Gank");//other request tag
    }

    private void weatherInit() {
        RxRetroHttp.getInstance()
                .setApiResultClass(WeatherApiResult.class)
                .setBaseUrl("https://www.sojson.com/open/api/weather/")
                .addClient(new SimpleRetroClient(), "Weather");
    }
}
