package com.skcodestack.fastec.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.ItemType;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MutipleItemEntity> convert() {
        ArrayList<MutipleItemEntity> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            Integer id = object.getInteger("id");
            String name = object.getString("name");

            MutipleItemEntity entity = new MutipleItemEntity.Builder()
                    .setField(MutipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MutipleFields.ID, id)
                    .setField(MutipleFields.NAME, name)
                    .setField(MutipleFields.TAG,false)
                    .build();
            list.add(entity);
        }
        //选中第一个
        list.get(0).setField(MutipleFields.TAG,true);
        return list;
    }
}
