package com.bakerj.demo.rxretrohttp.weather;

import com.bakerj.demo.rxretrohttp.entity.weather.Weather;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.annotation.RetroTag;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

@RetroTag(tag = "Weather")
public interface WeatherApi {
    @Headers({RxRetroHttp.MOCK_FILE_PATH_KEY + ":mock/weather.json",
            RxRetroHttp.MOCK_DELAY_KEY + ":1000"})
    @GET("json.shtml")
    Observable<Weather> getWeather(@Query("city") String city);
}
