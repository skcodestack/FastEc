package com.skcodestack.fastec.ec.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.personal.PersonOnClickListener;
import com.skcodestack.fastec.ec.main.personal.adapter.ListAdapter;
import com.skcodestack.fastec.ec.main.personal.adapter.ListBean;
import com.skcodestack.fastec.ec.main.personal.adapter.ListItemType;
import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class SettingDelegate extends LemonDelegate {
    //rv_settings
    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_SWITCH)
                .setId(1)
                .setText("消息推送")
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            IGlobalCallback callback = CallbackManager.getInstance().getCallback(CallbackType.OPEN_PUSH);
                            if (callback != null) {
                                callback.callback(null);
                            }
                            Toast.makeText(getContext(), "打开消息推送", Toast.LENGTH_SHORT).show();
                        } else {
                            IGlobalCallback callback = CallbackManager.getInstance().getCallback(CallbackType.CLOSE_PUSH);
                            if (callback != null) {
                                callback.callback(null);
                            }
                            Toast.makeText(getContext(), "关闭消息推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();

        ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_ARROW)
                .setId(2)
                .setDelegate(new AboutInfoDelegate())
                .setText("关于")
                .build();

        ArrayList mData = new ArrayList<>();
        mData.add(push);
        mData.add(about);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        ListAdapter mAdapter = new ListAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new PersonOnClickListener(this));
    }
}
