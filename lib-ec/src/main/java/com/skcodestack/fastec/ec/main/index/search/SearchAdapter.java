package com.skcodestack.fastec.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/24
 * Version  1.0
 * Description:
 */

public class SearchAdapter extends MutipleRecyclerAdapter {
    protected SearchAdapter(List<MutipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MutipleViewHolder helper, MutipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                AppCompatTextView tv_search = helper.getView(R.id.tv_search_item);
                String search = item.getField(MutipleFields.TEXT);
                tv_search.setText(search);
                break;
            default:
                break;
        }
    }
}
