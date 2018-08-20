package com.skcodestack.fastec.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class SectionDataConverter {

    final List<SectionItemEntity> convert(String json){
        List<SectionItemEntity> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseObject(json).getJSONArray("data");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            int id = object.getInteger("id") ;
            String section =object.getString("section");
            JSONArray goods = object.getJSONArray("goods");

            SectionItemEntity entity = new SectionItemEntity(true,section);
            entity.setId(id);
            entity.setIsMore(true);
            list.add(entity);

            int goods_size = goods.size();
            for (int j = 0; j < goods_size; j++) {
                JSONObject good_obj = goods.getJSONObject(j);
                Integer goods_id = good_obj.getInteger("goods_id");
                String goods_name = good_obj.getString("goods_name");
                String goods_thumb = good_obj.getString("goods_thumb");

                SectionContentItemEntity itemEntity =  new SectionContentItemEntity();
                itemEntity.setGoodsId(goods_id);
                itemEntity.setGoodsName(goods_name);
                itemEntity.setGoodsThumb(goods_thumb);

                list.add(new SectionItemEntity(itemEntity));
            }
        }

        return list;
    }

}
