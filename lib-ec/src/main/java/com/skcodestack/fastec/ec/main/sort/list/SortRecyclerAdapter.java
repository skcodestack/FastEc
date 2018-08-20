package com.skcodestack.fastec.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.main.sort.SortDelegate;
import com.skcodestack.fastec.ec.main.sort.content.ContentDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.ui.recycler.ItemType;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class SortRecyclerAdapter extends MutipleRecyclerAdapter {

    private final LemonDelegate SORTDELEGATE;
    private int mPrePosition;

    public SortRecyclerAdapter(List<MutipleItemEntity> data,SortDelegate sortDelegate) {
        super(data);
        this.SORTDELEGATE = sortDelegate;
        //加入布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MutipleViewHolder helper, final MutipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()){
            case ItemType.VERTICAL_MENU_LIST:
                String name = item.getField(MutipleFields.NAME);
                boolean isSelected = item.getField(MutipleFields.TAG);
                AppCompatTextView mItemName = helper.getView(R.id.tv_vertical_item_name);
                View mViewLine = helper.getView(R.id.view_line);
                mItemName.setText(name);
                View itemView = helper.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = helper.getAdapterPosition();
                        if(mPrePosition != currentPosition){
                            getData().get(mPrePosition).setField(MutipleFields.TAG,false);
                            notifyItemChanged(mPrePosition);

                            item.setField(MutipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;
                            //当前选中的ID
                            int contentId = getData().get(currentPosition).getField(MutipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if(!isSelected){
                    mViewLine.setVisibility(View.INVISIBLE);
                    mItemName.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_background));
                }else {
                    mViewLine.setVisibility(View.VISIBLE);
                    mViewLine.setBackgroundColor(ContextCompat.getColor(mContext,R.color.orange_dark));
                    mItemName.setTextColor(ContextCompat.getColor(mContext,R.color.orange_dark));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        ContentDelegate fragment = SORTDELEGATE.findChildFragment(ContentDelegate.class);
        if(fragment != null){
            fragment.replaceFragment(delegate,false);
        }
    }
}
