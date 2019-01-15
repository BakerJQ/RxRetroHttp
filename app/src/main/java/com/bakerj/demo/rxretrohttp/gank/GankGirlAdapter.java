package com.bakerj.demo.rxretrohttp.gank;

import android.widget.ImageView;

import com.bakerj.demo.rxretrohttp.R;
import com.bakerj.demo.rxretrohttp.entity.gank.GankGirl;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class GankGirlAdapter extends BaseQuickAdapter<GankGirl, BaseViewHolder> {
    public GankGirlAdapter() {
        super(R.layout.gank_item_girl);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankGirl item) {
        Glide.with(mContext).load(item.getUrl())
                .transition(new DrawableTransitionOptions().crossFade())
                .into((ImageView) helper.getView(R.id.iv_item_girl));
    }
}
