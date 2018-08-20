package com.skcodestack.fastec.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.personal.adapter.ListAdapter;
import com.skcodestack.fastec.ec.main.personal.adapter.ListBean;
import com.skcodestack.fastec.ec.main.personal.adapter.ListItemType;
import com.skcodestack.stack.delegates.LemonDelegate;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class ProfileDelegate extends LemonDelegate {
    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView;
    private ArrayList<ListBean> mList;

    @Override
    public Object getLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_AVATAR)
                .setId(1)
                .setImageUrl("https://img4.duitang.com/uploads/item/201403/29/20140329104049_xBeLm.thumb.200_200_c.jpeg")
                .build();
        ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_ARROW)
                .setId(2)
                .setText("姓名")
                .setValue("张聊聊")
                .build();
        ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_ARROW)
                .setId(3)
                .setText("生日")
                .setValue("1991-09-01")
                .build();

        mList = new ArrayList<>();
        mList.add(image);
        mList.add(name);
        mList.add(birth);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ListAdapter mAdapter = new ListAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new UserPofileOnClickListener(this));
    }
}
