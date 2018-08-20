package com.skcodestack.fastec.ec.main.personal.adapter;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.skcodestack.stack.delegates.LemonDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private LemonDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    private ListBean(int mItemType, String mImageUrl, String mText, String mValue, int mId, LemonDelegate mDelegate, CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.mDelegate = mDelegate;
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public int getItemViewType() {
        return mItemType;
    }

    public String getImageUrl() {
        if (mImageUrl == null) {
            return "";
        }
        return mImageUrl;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public LemonDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static class Builder {

        private int mItemType = 0;
        private String mImageUrl = null;
        private String mText = null;
        private String mValue = null;
        private int mId = 0;
        private LemonDelegate mDelegate = null;
        private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;


        public Builder setItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setImageUrl(String mImageUrl) {
            this.mImageUrl = mImageUrl;
            return this;
        }

        public Builder setText(String mText) {
            this.mText = mText;
            return this;
        }

        public Builder setValue(String mValue) {
            this.mValue = mValue;
            return this;
        }

        public Builder setId(int mId) {
            this.mId = mId;
            return this;
        }

        public Builder setDelegate(LemonDelegate mDelegate) {
            this.mDelegate = mDelegate;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
            this.mOnCheckedChangeListener = mOnCheckedChangeListener;
            return this;
        }

        public ListBean build() {
            return new ListBean(mItemType, mImageUrl, mText, mValue, mId, mDelegate, mOnCheckedChangeListener);
        }
    }
}
