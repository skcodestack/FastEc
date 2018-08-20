package com.skcodestack.fastec.ec.main.personal.adapter;

import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;

import java.io.BufferedReader;
import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {
    private static final RequestOptions REQUEST_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_TYPE_ARROW, R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_TYPE_AVATAR, R.layout.arrow_item_avatar);
        addItemType(ListItemType.ITEM_TYPE_SWITCH, R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (item.getItemViewType()) {
            case ListItemType.ITEM_TYPE_ARROW:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_TYPE_AVATAR:
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(REQUEST_OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_arrow_avatar));
                break;
            case ListItemType.ITEM_TYPE_SWITCH:
                helper.setText(R.id.tv_arrow_switch_text, item.getText());
                SwitchCompat mSwitchView =  helper.getView(R.id.list_item_switch);
                mSwitchView.setChecked(true);
                mSwitchView.setOnCheckedChangeListener(item.getOnCheckedChangeListener());
                break;
            default:
                break;
        }
    }
}
