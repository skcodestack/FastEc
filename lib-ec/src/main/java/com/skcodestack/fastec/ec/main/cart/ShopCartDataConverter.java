package com.skcodestack.fastec.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/17
 * Version  1.0
 * Description:
 */

public class ShopCartDataConverter extends DataConverter {

    @Override
    public ArrayList<MutipleItemEntity> convert() {
        ArrayList<MutipleItemEntity> list = new ArrayList<>();
        JSONObject object =
                JSON.parseObject(getJsonData());
        JSONArray jsonArray = object.getJSONArray("data");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");
            String title = jsonObject.getString("title");
            String thumb = jsonObject.getString("thumb");
            String desc = jsonObject.getString("desc");
            Double price = jsonObject.getDouble("price");
            Integer count = jsonObject.getInteger("count");

            MutipleItemEntity entity = new MutipleItemEntity.Builder()
                    .setField(MutipleFields.ITEM_TYPE, ShopCardItemType.SHOP_CART_ITEM)
                    .setField(ShopCardItemFields.ID, id)
                    .setField(ShopCardItemFields.TITLE, title)
                    .setField(ShopCardItemFields.THUMB, thumb)
                    .setField(ShopCardItemFields.DESC, desc)
                    .setField(ShopCardItemFields.PRICE, price)
                    .setField(ShopCardItemFields.COUNT, count)
                    .setField(ShopCardItemFields.IS_SELECTED, false)
                    .setField(ShopCardItemFields.POSITION,i)
                    .build();
            list.add(entity);
        }
        return list;
    }
}
