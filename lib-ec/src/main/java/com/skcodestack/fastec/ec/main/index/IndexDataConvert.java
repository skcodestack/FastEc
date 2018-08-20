package com.skcodestack.fastec.ec.main.index;

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
 * Created by sk on 2018/7/14
 * Version  1.0
 * Description:
 */

public class IndexDataConvert extends DataConverter {

    @Override
    public ArrayList<MutipleItemEntity> convert() {
        ArrayList<MutipleItemEntity> mList = new ArrayList<>();

        JSONArray data =
                JSON.parseObject(getJsonData()).getJSONArray("data");

        int size = data.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            String imageUrl = jsonObject.getString("imageUrl");
            String text = jsonObject.getString("text");
            Integer goodsId = jsonObject.getInteger("goodsId");
            Integer spanSize = jsonObject.getInteger("spanSize");
            JSONArray banners = jsonObject.getJSONArray("banners");

            ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null && text != null) {
                type = ItemType.TEXT_IMAGE;
            } else if (banners != null) {
                type = ItemType.BANNER;
                int bSize = banners.size();
                for (int j = 0; j < bSize; j++) {
                    String url = banners.getString(j);
                    bannerImages.add(url);
                }
            }

            MutipleItemEntity entity = new MutipleItemEntity.Builder()
                    .setField(MutipleFields.ID, goodsId)
                    .setItemType(type)
                    .setField(MutipleFields.BANNERS, bannerImages)
                    .setField(MutipleFields.IMAGE_URL, imageUrl)
                    .setField(MutipleFields.TEXT, text)
                    .setField(MutipleFields.SPAN_SIZE, spanSize)
                    .build();
            mList.add(entity);
        }


        return mList;
    }
}
