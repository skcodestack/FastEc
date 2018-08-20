package com.skcodestack.fastec.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.ui.widget.AutoPhotoLayout;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/22
 * Version  1.0
 * Description:
 */

public class OrderCommentDelegate extends LemonDelegate {

    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout;

    @Override
    public Object getLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
            @Override
            public void callback(@Nullable Uri uri) {
                mAutoPhotoLayout.onCropTarget(uri);
            }
        });
    }
}
