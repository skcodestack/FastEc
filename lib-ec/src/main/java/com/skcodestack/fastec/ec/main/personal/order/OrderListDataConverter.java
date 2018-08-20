package com.skcodestack.fastec.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class OrderListDataConverter extends DataConverter {
    @Override
    public ArrayList<MutipleItemEntity> convert() {
        JSONArray data = JSON.parseObject(getJsonData()).getJSONArray("data");
        ArrayList<MutipleItemEntity> list = new ArrayList<>();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            JSONObject object =
                    data.getJSONObject(i);
            String thumb = object.getString("thumb");
            String title = object.getString("title");
            String time = object.getString("time");
            double price = object.getDouble("price");
            int id = object.getInteger("id");

            MutipleItemEntity entity = new MutipleItemEntity.Builder()
                    .setField(MutipleFields.ITEM_TYPE, OrderListItemType.ITEM_TYPE_ORDER)
                    .setField(OrderItemFields.ID, id)
                    .setField(OrderItemFields.PRICE, price)
                    .setField(OrderItemFields.THUMB, thumb)
                    .setField(OrderItemFields.TIME, time)
                    .setField(OrderItemFields.TITLE, title)
                    .build();
            list.add(entity);

        }
        return list;
    }
}
