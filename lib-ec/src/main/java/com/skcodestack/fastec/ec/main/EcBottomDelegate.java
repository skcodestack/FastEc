package com.skcodestack.fastec.ec.main;

import android.graphics.Color;

import com.skcodestack.fastec.ec.main.cart.ShopCartDelegate;
import com.skcodestack.fastec.ec.main.discover.DiscoverDelegate;
import com.skcodestack.fastec.ec.main.index.IndexDelegate;
import com.skcodestack.fastec.ec.main.personal.PersonalDelegate;
import com.skcodestack.fastec.ec.main.sort.SortDelegate;
import com.skcodestack.stack.delegates.bottom.BaseBottomDelegate;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;
import com.skcodestack.stack.delegates.bottom.BottomTabBean;
import com.skcodestack.stack.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {

        LinkedHashMap<BottomTabBean, BottomItemDelegate> build = new LinkedHashMap<>();
        build.put(new BottomTabBean("{fa-home}", "首页"), new IndexDelegate());
        build.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        build.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        build.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        build.put(new BottomTabBean("{fa-user}", "wod"), new PersonalDelegate());
        return builder.addItem(build).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setSelectedColor() {
        return Color.parseColor("#ffff8800");
    }
}
