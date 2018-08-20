package com.skcodestack.stack.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.stack.R;
import com.skcodestack.stack.R2;
import com.skcodestack.stack.delegates.LemonDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public abstract class BaseBottomDelegate extends LemonDelegate implements View.OnClickListener {

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;


    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomTabBean> ITEM_TABS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mSelectedColor = Color.RED;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setSelectedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setSelectedColor() != 0) {
            mSelectedColor = setSelectedColor();
        }

        LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(ItemBuilder.builder());
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> entry : ITEMS.entrySet()) {
            BottomTabBean key = entry.getKey();
            BottomItemDelegate value = entry.getValue();
            ITEM_TABS.add(key);
            ITEM_DELEGATES.add(value);
        }

    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.bottom_icon_text_layout, mBottomBar);
            RelativeLayout relativeLayout = (RelativeLayout) mBottomBar.getChildAt(i);
            relativeLayout.setTag(i);
            relativeLayout.setOnClickListener(this);
            IconTextView itemIcon = (IconTextView) relativeLayout.getChildAt(0);
            AppCompatTextView itemTitle = (AppCompatTextView) relativeLayout.getChildAt(1);
            BottomTabBean tabBean = ITEM_TABS.get(i);

            itemIcon.setText(tabBean.getIcon());
            itemTitle.setText(tabBean.getTitle());
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mSelectedColor);
                itemTitle.setTextColor(mSelectedColor);
            }
        }
        SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_content, mIndexDelegate, delegateArray);
    }

    private void resetColor() {
        int childCount = mBottomBar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setTextColor(Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        resetColor();
        RelativeLayout item = (RelativeLayout) v;
        IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setTextColor(mSelectedColor);
        itemTitle.setTextColor(mSelectedColor);

        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        //当前delegate
        mCurrentDelegate = tag;
    }
}
