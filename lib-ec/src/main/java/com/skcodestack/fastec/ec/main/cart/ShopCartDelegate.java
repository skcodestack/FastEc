package com.skcodestack.fastec.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.pay.FastPay;
import com.skcodestack.fastec.ec.pay.IAliPayResultListener;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/17
 * Version  1.0
 * Description:
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener, IAliPayResultListener {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectedAll;
    @BindView(R2.id.view_stub)
    ViewStubCompat mStubEmpty;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView tvTotalPrice;
    private ShopCartAdapter mAdapter;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectedAll() {
        boolean isSelected = (boolean) mIconSelectedAll.getTag();
        if (isSelected) {
            mIconSelectedAll.setTextColor(Color.GRAY);
            mIconSelectedAll.setTag(false);
            mAdapter.setSelectedAll(false);
        } else {
            mIconSelectedAll.setTextColor(ContextCompat.getColor(getContext(), R.color.orange_dark));
            mIconSelectedAll.setTag(true);
            mAdapter.setSelectedAll(true);
        }
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_remove)
    void onClickRemoveSelectedItem() {
        mAdapter.removeSelectedItem();
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClearAllItem() {
        mAdapter.clearAllItem();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
        createOrder();
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        //服务器订单地址（本地服务器生成订单）
        String orderUrl = "http://app.api.zanzuanshi.com/api/v1/peyment";
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("userId", "1");
        map.put("amount", 0.01);//支付的金额
        //其他传给服务器的信息
        RestClient.builder()
                .url(orderUrl)
                .params(map)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //提交订单完成后，进行真正的支付功能
                        //获取订单号
                        Integer orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .setAliPayResultListener(ShopCartDelegate.this)
                                .beginPayDialog();

                    }
                }).build()
                .post();
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectedAll.setTag(false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder().url("shop_cart").success(this).build().get();
    }

    @Override
    public void onSuccess(String response) {
        ArrayList<MutipleItemEntity> entities = new ShopCartDataConverter().setJsonData(response).convert();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShopCartAdapter(entities);
        mAdapter.setICartItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
        checkItemCount();
        setTotalPrice(mAdapter.getTotalPrice());
    }

    private void checkItemCount() {
        int itemCount = mAdapter.getItemCount();
        if (itemCount == 0) {
            //防止重复inflate
            if (mStubEmpty.getParent() != null) {
                mStubEmpty.inflate();
            }
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(double mTotalPrice) {
        setTotalPrice(mAdapter.getTotalPrice());
    }

    private void setTotalPrice(double price) {
        tvTotalPrice.setText("￥" + price);
    }


    @Override
    public void onPayConnectionError() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancle() {

    }
}
