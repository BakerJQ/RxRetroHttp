package com.bakerj.demo.rxretrohttp.weather;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bakerj.demo.rxretrohttp.BaseActivity;
import com.bakerj.demo.rxretrohttp.R;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.ToastUtils;

public class WeatherActivity extends BaseActivity {
    private EditText editText;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        editText = findViewById(R.id.et_weather);
        textView = findViewById(R.id.tv_weather_result);
        button = findViewById(R.id.btn_weather_search);
        button.setOnClickListener(v -> RxRetroHttp.composeRequest
                (RxRetroHttp.create(WeatherApi.class, "Weather").getWeather
                        (editText.getText().toString()), this, "Weather")
                .subscribe(new ApiObserver<Weather>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        button.setEnabled(false);
                    }

                    @Override
                    protected void success(Weather data) {
                        textView.setText(data.toString());
                        button.setEnabled(true);
                    }

                    @Override
                    protected void error(Throwable t) {
                        super.error(t);
                        button.setEnabled(true);
                        ToastUtils.showShort(t.getMessage());
                    }
                }));
    }
}
