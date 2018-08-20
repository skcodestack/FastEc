package com.skcodestack.stack.ui.recycler;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public abstract class DataConverter {

    protected final ArrayList<MutipleItemEntity> ENTITYS = new ArrayList<>();
    private String mJsonData;

    public abstract ArrayList<MutipleItemEntity> convert();

    public DataConverter setJsonData(String json) {

        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || "".equals(mJsonData)) {
            throw new RuntimeException("json data is null");
        }
        return mJsonData;
    }
}
