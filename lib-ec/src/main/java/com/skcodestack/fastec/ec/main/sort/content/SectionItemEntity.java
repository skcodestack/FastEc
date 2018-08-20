package com.skcodestack.fastec.ec.main.sort.content;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class SectionItemEntity extends SectionEntity<SectionContentItemEntity> {

    private boolean mIsMore = false;
    private int mId = -1;

    public SectionItemEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionItemEntity(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }


    public boolean isIsMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
