package com.skcodestack.fastec.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class AddressDataConverter extends DataConverter {
    @Override
    public ArrayList<MutipleItemEntity> convert() {
        JSONArray jsonArray =
                JSON.parseObject(getJsonData()).getJSONArray("data");
        ArrayList<MutipleItemEntity> list = new ArrayList<>();
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject =
                    jsonArray.getJSONObject(i);
            int id = jsonObject.getInteger("id");
            boolean isDefault = jsonObject.getBoolean("default");
            String name = jsonObject.getString("name");
            String phone = jsonObject.getString("phone");
            String address = jsonObject.getString("address");

            MutipleItemEntity entity = new MutipleItemEntity.Builder()
                    .setItemType(AddressItemType.ITEM_TYPE_ADDRESS)
                    .setField(AddressItemFeilds.ID, id)
                    .setField(AddressItemFeilds.DEFAULT, isDefault)
                    .setField(AddressItemFeilds.NAME, name)
                    .setField(AddressItemFeilds.ADDRESS, address)
                    .setField(AddressItemFeilds.PHONE, phone)
                    .build();
            list.add(entity);
        }

        return list;
    }
}
