package com.skcodestack.stack.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.skcodestack.stack.R;
import com.skcodestack.stack.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/14
 * Version  1.0
 * Description:
 */

public class MutipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MutipleItemEntity, MutipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    private boolean isInitBanner = false;
    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected MutipleRecyclerAdapter(List<MutipleItemEntity> data) {
        super(data);
        init();
    }

    public static MutipleRecyclerAdapter create(List<MutipleItemEntity> data) {
        return new MutipleRecyclerAdapter(data);
    }

    public static MutipleRecyclerAdapter create(DataConverter converter) {
        return new MutipleRecyclerAdapter(converter.convert());
    }

    private void init() {
        //不同布局
        addItemType(ItemType.TEXT, R.layout.item_mutiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_mutiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_mutiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_mutiple_banner);

        //宽度监听
        setSpanSizeLookup(this);
        //打开加载动画且一直打开
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected MutipleViewHolder createBaseViewHolder(View view) {
        return MutipleViewHolder.create(view);
    }

    @Override
    protected void convert(MutipleViewHolder helper, MutipleItemEntity item) {
        String text;
        String imageUrl;
        ArrayList<String> banners;
        switch (helper.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(MutipleFields.TEXT);
                helper.setText(R.id.text_signle, text);
                break;
            case ItemType.TEXT_IMAGE:
                text = item.getField(MutipleFields.TEXT);
                imageUrl = item.getField(MutipleFields.IMAGE_URL);
                helper.setText(R.id.text_mutiple, text);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) helper.getView(R.id.image_mutiple));
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(MutipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) helper.getView(R.id.image_signle));
                break;
            case ItemType.BANNER:
                if(!isInitBanner){
                    ArrayList<String> bannerList = item.getField(MutipleFields.BANNERS);
                    ConvenientBanner convenientBanner = helper.getView(R.id.banner_recycler_item);
                    BannerCreator.setDeafult(mContext,convenientBanner,bannerList,this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MutipleFields.SPAN_SIZE);
    }

    /**
     * bannder  监听
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }
}
