package com.skcodestack.stack.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skcodestack.stack.R;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class ImageHolder extends Holder<String> {

    private AppCompatImageView mImageView;
    private Context mContext;
    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public ImageHolder(Context context,View itemView)
    {
        super(itemView);
        mContext = context;
    }

    @Override
    protected void initView(View itemView) {
        mImageView = (AppCompatImageView) itemView.findViewById(R.id.image_item);
    }

    @Override
    public void updateUI(String data) {
        Glide.with(mContext)
                .load(data)
                .apply(RECYCLER_OPTIONS)
                .into(mImageView);
    }
}
