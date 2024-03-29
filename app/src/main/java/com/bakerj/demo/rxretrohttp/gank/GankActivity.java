package com.bakerj.demo.rxretrohttp.gank;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.demo.rxretrohttp.BaseActivity;
import com.bakerj.demo.rxretrohttp.R;
import com.bakerj.demo.rxretrohttp.entity.gank.GankGirl;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;

import java.util.List;

public class GankActivity extends BaseActivity {
    private GankGirlAdapter adapter = new GankGirlAdapter();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        recyclerView = findViewById(R.id.rv_gank);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        RxRetroHttp.composeRequest(RxRetroHttp.create(GankApi.class).getGankGirls(),
                this, GankApi.class)
                .subscribe(new ApiObserver<List<GankGirl>>(this) {
                    @Override
                    protected void success(List<GankGirl> data) {
                        adapter.setNewData(data);
                    }
                });
    }
}
