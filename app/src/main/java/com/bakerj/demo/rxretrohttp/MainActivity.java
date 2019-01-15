package com.bakerj.demo.rxretrohttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bakerj.demo.rxretrohttp.gank.GankActivity;
import com.bakerj.demo.rxretrohttp.github.GithubActivity;
import com.bakerj.demo.rxretrohttp.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_github).setOnClickListener(v -> startActivity(new Intent(MainActivity
                .this, GithubActivity.class)));
        findViewById(R.id.btn_gank).setOnClickListener(v -> startActivity(new Intent(MainActivity
                .this, GankActivity.class)));
        findViewById(R.id.btn_weather).setOnClickListener(v -> startActivity(new Intent(MainActivity
                .this, WeatherActivity.class)));
    }
}
