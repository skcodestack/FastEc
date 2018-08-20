package com.skcodestack.stack.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public class MutipleItemEntity implements MultiItemEntity {

    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MUTIPLE_FIELDS = new LinkedHashMap<>();
    private SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE = new SoftReference<LinkedHashMap<Object, Object>>(MUTIPLE_FIELDS, ITEM_QUEUE);

    private MutipleItemEntity(LinkedHashMap<Object, Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MutipleFields.ITEM_TYPE);
    }

    public final <T> T getField(Object key) {
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?, ?> getFields() {
        return FIELDS_REFERENCE.get();
    }

    public final MutipleItemEntity setField(Object key, Object value) {
        FIELDS_REFERENCE.get().put(key, value);
        return this;
    }

    public static class Builder {
        final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

        public Builder() {
            FIELDS.clear();

        }

        public final Builder setItemType(int itemType) {
            FIELDS.put(MutipleFields.ITEM_TYPE, itemType);
            return this;
        }

        public final Builder setField(Object key, Object value) {
            FIELDS.put(key, value);
            return this;
        }

        public final Builder setField(Map<Object, Object> map) {
            FIELDS.putAll(map);
            return this;
        }

        public final MutipleItemEntity build() {
            return new MutipleItemEntity(FIELDS);
        }
    }
}
