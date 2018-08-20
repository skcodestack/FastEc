package com.skcodestack.stack.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();


    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean tab, BottomItemDelegate delegate) {
        ITEMS.put(tab, delegate);
        return this;
    }
    public final ItemBuilder addItem(LinkedHashMap<BottomTabBean, BottomItemDelegate> list) {
        ITEMS.putAll(list);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build(){
        return ITEMS;
    }
}
