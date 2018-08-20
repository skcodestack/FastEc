package com.skcodestack.fastec.ec.main.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/24
 * Version  1.0
 * Description:
 */

public class GoodsInfoDelegate extends LemonDelegate {

    private static final String GOODS_INFO = "GOODS_INFO";
    private JSONObject mData;

    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mGoodsInfoTitle = null;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mGoodsInfoDesc = null;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mGoodsInfoPrice = null;

    public static GoodsInfoDelegate create(String goodsInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(GOODS_INFO, goodsInfo);

        GoodsInfoDelegate delegate = new GoodsInfoDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        String json = arguments.getString(GOODS_INFO);
        mData = JSON.parseObject(json);
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_goods_info;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        String name = mData.getString("name");
        String desc = mData.getString("description");
        Double price = mData.getDouble("price");
        mGoodsInfoTitle.setText(name);
        mGoodsInfoDesc.setText(desc);
        mGoodsInfoPrice.setText(String.valueOf(price));
    }
}
