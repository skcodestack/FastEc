package com.skcodestack.fastec.ec.main.personal.order;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class OrderListAdapter extends MutipleRecyclerAdapter {


    private static final RequestOptions REQUEST_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected OrderListAdapter(List<MutipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_TYPE_ORDER, R.layout.item_order_list);
    }

    @Override
    protected void convert(MutipleViewHolder helper, MutipleItemEntity item) {
        super.convert(helper, item);
        int itemType = item.getItemType();
        switch (itemType) {
            case OrderListItemType.ITEM_TYPE_ORDER:
                String title = item.getField(OrderItemFields.TITLE);
                String time = item.getField(OrderItemFields.TIME);
                String thumb = item.getField(OrderItemFields.THUMB);
                double price = item.getField(OrderItemFields.PRICE);
                int id = item.getField(OrderItemFields.ID);

                ImageView imageView = helper.getView(R.id.image_order_list);

                helper.setText(R.id.tv_order_list_title, title);
                helper.setText(R.id.tv_order_list_price, String.valueOf(price));
                helper.setText(R.id.tv_order_list_time, time);

                Glide.with(mContext)
                        .load(thumb)
                        .apply(REQUEST_OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
