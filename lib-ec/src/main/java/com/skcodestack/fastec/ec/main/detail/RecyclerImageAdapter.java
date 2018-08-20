package com.skcodestack.fastec.ec.main.detail;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.ui.recycler.ItemType;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/25
 * Version  1.0
 * Description:
 */

public class RecyclerImageAdapter extends MutipleRecyclerAdapter {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .dontAnimate();

    protected RecyclerImageAdapter(List<MutipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_load_image);
    }

    @Override
    protected void convert(MutipleViewHolder helper, MutipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case ItemType.SINGLE_BIG_IMAGE:
                final AppCompatImageView imageView = helper.getView(R.id.image_item);
                final String url = item.getField(MutipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(url)
                        .apply(OPTIONS)
                        .into(imageView);

                break;
            default:
                break;
        }
    }
}
