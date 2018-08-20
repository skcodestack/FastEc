package com.skcodestack.stack.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.stack.R;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/22
 * Version  1.0
 * Description:
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    private static final CharSequence ICON_UN_SELECTED = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";
    private static final int STAR_COUNT = 5;

    private static final ArrayList<IconTextView> STARS = new ArrayList<>();


    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }

    private void initStarIcon() {
        for (int i = 0; i < STAR_COUNT; i++) {
            IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            star.setLayoutParams(params);
            star.setText(ICON_UN_SELECTED);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this);
            STARS.add(star);
            this.addView(star);
        }
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            IconTextView view = STARS.get(i);
            view.setText(ICON_SELECTED);
            view.setTextColor(Color.RED);
            view.setTag(R.id.star_is_select, true);
        }
    }

    private void unSelectStar(int count) {
        for (int i = count + 1; i < STAR_COUNT; i++) {
            IconTextView view = STARS.get(i);
            view.setText(ICON_UN_SELECTED);
            view.setTextColor(Color.GRAY);
            view.setTag(R.id.star_is_select, false);
        }

    }


    private int getStarCount() {
        int count = 0;
        for (int i = 0; i < STAR_COUNT; i++) {
            IconTextView star = STARS.get(i);
            boolean isSelected = (boolean) star.getTag(R.id.star_is_select);
            if(isSelected){
                count++;
            }
        }
        return count;
    }

    @Override
    public void onClick(View v) {
        IconTextView star = (IconTextView) v;
        int index = (int) star.getTag(R.id.star_count);
        boolean isSelected = (boolean) star.getTag(R.id.star_is_select);
        if (isSelected) {
            unSelectStar(index);
        } else {
            selectStar(index);
        }
    }
}
