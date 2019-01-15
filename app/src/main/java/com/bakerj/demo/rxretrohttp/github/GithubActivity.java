package com.bakerj.demo.rxretrohttp.github;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakerj.demo.rxretrohttp.BaseActivity;
import com.bakerj.demo.rxretrohttp.R;
import com.bakerj.demo.rxretrohttp.entity.github.GithubUser;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class GithubActivity extends BaseActivity {
    private TextView textView;
    private Button button;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        textView = findViewById(R.id.tv_github_name);
        button = findViewById(R.id.btn_github_search);
        editText = findViewById(R.id.et_github);
        imageView = findViewById(R.id.iv_github_avatar);
        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                textView.setText("please enter user name");
                return;
            }
            RxRetroHttp.composeRequest(RxRetroHttp.create(GithubApi.class).getUser(editText
                    .getText().toString()), this)
                    .subscribe(new ApiObserver<GithubUser>(this) {
                        @Override
                        protected void onStart() {
                            super.onStart();
                            button.setEnabled(false);
                        }

                        @Override
                        protected void success(GithubUser data) {
                            textView.setText(data.getName());
                            Glide.with(imageView).load(data.getAvatar_url())
                                    .transition(new DrawableTransitionOptions().crossFade())
                                    .into(imageView);
                            button.setEnabled(true);
                        }

                        @Override
                        protected void error(Throwable t) {
                            super.error(t);
                            textView.setText(t.getMessage());
                            button.setEnabled(true);
                        }
                    });
        });
    }
}
