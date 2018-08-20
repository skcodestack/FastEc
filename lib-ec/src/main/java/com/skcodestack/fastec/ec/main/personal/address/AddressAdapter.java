package com.skcodestack.fastec.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;
import com.skcodestack.stack.ui.recycler.MutipleViewHolder;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class AddressAdapter extends MutipleRecyclerAdapter {

    protected AddressAdapter(List<MutipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_TYPE_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MutipleViewHolder helper, MutipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case AddressItemType.ITEM_TYPE_ADDRESS:
                int id = item.getField(AddressItemFeilds.ID);
                String name = item.getField(AddressItemFeilds.NAME);
                String phone = item.getField(AddressItemFeilds.PHONE);
                String address = item.getField(AddressItemFeilds.ADDRESS);
                boolean isDefault = item.getField(AddressItemFeilds.DEFAULT);

                AppCompatTextView tv_name = helper.getView(R.id.tv_address_name);
                AppCompatTextView tv_phone = helper.getView(R.id.tv_address_phone);
                AppCompatTextView tv_delete = helper.getView(R.id.tv_address_delete);
                AppCompatTextView tv_address = helper.getView(R.id.tv_address_address);

                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                .url("opration")
                                .loader(mContext)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(helper.getAdapterPosition());
                                    }
                                })
                                .build()
                                .get();
                    }
                });

                tv_name.setText(name);
                tv_phone.setText(phone);
                tv_address.setText(address);
                break;
            default:
                break;
        }
    }
}
