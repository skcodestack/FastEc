package com.skcodestack.fastec.ec.main.sort.content;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.skcodestack.fastec.ec.R;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class SectionRecyclerAdapter extends BaseSectionQuickAdapter<SectionItemEntity, BaseViewHolder> {

    private final RequestOptions REQUEST_OPTIONS =new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public SectionRecyclerAdapter(int layoutResId, int sectionHeadResId, List<SectionItemEntity> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionItemEntity item) {
        helper.setText(R.id.tv_header,item.header);
        helper.setVisible(R.id.tv_more,item.isIsMore());
        helper.addOnClickListener(R.id.tv_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionItemEntity item) {
        int goodsId = item.t.getGoodsId();
        String goodsName = item.t.getGoodsName();
        String goodsThumb = item.t.getGoodsThumb();

        helper.setText(R.id.tv_section_content,goodsName);
        ImageView iv_section_content = helper.getView(R.id.iv_section_content);
        Glide.with(mContext)
                .load(goodsThumb)
                .apply(REQUEST_OPTIONS)
                .into(iv_section_content);
    }
}
