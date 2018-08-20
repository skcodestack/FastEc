package com.skcodestack.fastec.ec.main.cart;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;
import com.skcodestack.stack.util.log.LemonLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/17
 * Version  1.0
 * Description:
 */

public class ShopCartAdapter extends MutipleRecyclerAdapter {


    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    private boolean mIsSelectedAll = false;
    private ICartItemListener mICartItemListener;
    private double mTotalPrice = 0.00;

    public void setICartItemListener(ICartItemListener mICartItemListener) {
        this.mICartItemListener = mICartItemListener;
    }

    protected ShopCartAdapter(List<MutipleItemEntity> data) {
        super(data);
        for (MutipleItemEntity entity : data) {
            double price = entity.getField(ShopCardItemFields.PRICE);
            int count = entity.getField(ShopCardItemFields.COUNT);
            mTotalPrice += price * count;
        }
        //加入布局
        addItemType(ShopCardItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    @Override
    protected void convert(MutipleViewHolder helper, final MutipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case ShopCardItemType.SHOP_CART_ITEM:
                int id = item.getField(ShopCardItemFields.ID);
                int count = item.getField(ShopCardItemFields.COUNT);
                final double price = item.getField(ShopCardItemFields.PRICE);
                String desc = item.getField(ShopCardItemFields.DESC);
                String title = item.getField(ShopCardItemFields.TITLE);
                String thumb = item.getField(ShopCardItemFields.THUMB);

                final IconTextView iconSelected = helper.getView(R.id.icon_item_shop_cart);
                AppCompatImageView imageView = helper.getView(R.id.image_item_shop_cart);
                AppCompatTextView titleTextView = helper.getView(R.id.tv_item_shop_cart_title);
                AppCompatTextView descTextView = helper.getView(R.id.tv_item_shop_cart_desc);
                AppCompatTextView priceTextView = helper.getView(R.id.tv_item_shop_cart_price);
                IconTextView iconMinus = helper.getView(R.id.icon_item_minus);
                final AppCompatTextView countTextView = helper.getView(R.id.tv_item_count);
                IconTextView iconPlus = helper.getView(R.id.icon_item_plus);

                titleTextView.setText(title);
                descTextView.setText(desc);
                priceTextView.setText(String.valueOf(price));
                countTextView.setText(String.valueOf(count));

                Glide.with(mContext)
                        .load(thumb)
                        .apply(REQUEST_OPTIONS)
                        .into(imageView);

                item.setField(ShopCardItemFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = item.getField(ShopCardItemFields.IS_SELECTED);

                if (isSelected) {
                    iconSelected.setTextColor(ContextCompat.getColor(mContext, R.color.orange_dark));
                } else {
                    iconSelected.setTextColor(Color.GRAY);
                }
                iconSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = item.getField(ShopCardItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconSelected.setTextColor(Color.GRAY);
                        } else {
                            iconSelected.setTextColor(ContextCompat.getColor(mContext, R.color.orange_dark));
                        }
                        item.setField(ShopCardItemFields.IS_SELECTED, !currentSelected);
                    }
                });
                //增加
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = item.getField(ShopCardItemFields.PRICE);
                        int count = Integer.parseInt(countTextView.getText().toString());
                        count++;
                        countTextView.setText(String.valueOf(count));
                        item.setField(ShopCardItemFields.COUNT, count);
                        mTotalPrice +=price;
                        if(mICartItemListener != null){
                            double mItemTotal = count * price;
                            mICartItemListener.onItemClick(mItemTotal);
                        }
                    }
                });
                //减少
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(countTextView.getText().toString());
                        double price = item.getField(ShopCardItemFields.PRICE);
                        if (count > 1) {
                            count--;
                            countTextView.setText(String.valueOf(count));
                            item.setField(ShopCardItemFields.COUNT, count);
                            mTotalPrice -=price;
                            if(mICartItemListener != null){
                                double mItemTotal = count * price;
                                mICartItemListener.onItemClick(mItemTotal);
                            }
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    public void setSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }


    private List<MutipleItemEntity> getCanBeRemovedItems() {

        List<MutipleItemEntity> data = getData();
        List<MutipleItemEntity> canRemovedList = new ArrayList<>();
        for (MutipleItemEntity entity : data) {
            boolean isSelected = entity.getField(ShopCardItemFields.IS_SELECTED);
            if (isSelected) {
                canRemovedList.add(entity);
            }
        }
        Collections.sort(canRemovedList, new Comparator<MutipleItemEntity>() {
            @Override
            public int compare(MutipleItemEntity o1, MutipleItemEntity o2) {
                int position1 = o1.getField(ShopCardItemFields.POSITION);
                int position2 = o2.getField(ShopCardItemFields.POSITION);
                if (position1 == position2) {
                    return 0;
                }
                return (position1 > position2) ? 1 : -1;
            }
        });

        return canRemovedList;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void removeSelectedItem() {
        List<MutipleItemEntity> removeItems = getCanBeRemovedItems();
        int offset = 0;
        for (MutipleItemEntity item : removeItems) {
            int postion = item.getField(ShopCardItemFields.POSITION);
            postion = postion - offset;
            LemonLogger.e("list", "position:" + postion + "---" + getItemCount());
            if (postion < getItemCount()) {
                remove(postion);
                offset++;
            }
        }
//        notifyDataSetChanged();
    }

    public void clearAllItem() {
        getData().clear();
        notifyDataSetChanged();
    }
}
