package com.skcodestack.fastec.ec.main.personal.profile;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.main.personal.adapter.ListBean;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;

import java.net.URI;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class UserPofileOnClickListener extends SimpleClickListener {

    private final LemonDelegate DELEGATE;

    public UserPofileOnClickListener(LemonDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //头像
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void callback(Uri uri) {
                        ImageView iv_user = (ImageView) view.findViewById(R.id.img_arrow_avatar);
                        Glide.with(DELEGATE)
                                .load(uri)
                                .into(iv_user);
//                        RestClient.builder()
//                                .url("upload_img")
//                                .file(uri.getPath())
//                                .loader(DELEGATE.getContext())
//                                .success(new ISuccess() {
//                                    @Override
//                                    public void onSuccess(String response) {
//                                        String path = JSON.parseObject(response).getString("path");
//                                        //上传头像成功，更新user信息
//                                        RestClient.builder()
//                                                .url("update_user_info")
//                                                .params("avatar", path)
//                                                .loader(DELEGATE.getContext())
//                                                .success(new ISuccess() {
//                                                    @Override
//                                                    public void onSuccess(String response) {
//                                                        //获取跟新后的user信息，同步到数据库
//                                                    }
//                                                })
//                                                .build()
//                                                .post();
//                                    }
//                                })
//                                .build()
//                                .upload();
                    }
                });
                DELEGATE.startCamera();
                break;
            case 2:
                //姓名
                break;
            case 3:
                //生日
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
