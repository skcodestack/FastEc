package com.skcodestack.fastec.ec.main.index.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.util.log.LemonLogger;
import com.skcodestack.stack.util.storage.LemonPreference;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/24
 * Version  1.0
 * Description:
 */

public class SearchDataConverter extends DataConverter {
    public static final String SEARCH_FLAG = "search_history";

    @Override
    public ArrayList<MutipleItemEntity> convert() {
        String search_history = LemonPreference.getCustomAppProfile(SEARCH_FLAG);
        LemonLogger.e("SearchDataConverter",search_history);
        if (!search_history.equals("")) {
            JSONArray array = JSONArray.parseArray(search_history);
            int size = array.size();
            for (int i = 0; i < size; i++) {
                String s = array.getString(i);
                MutipleItemEntity entity = new MutipleItemEntity.Builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MutipleFields.TEXT, s)
                        .build();
                ENTITYS.add(entity);
            }
        }
        return ENTITYS;
    }
}
