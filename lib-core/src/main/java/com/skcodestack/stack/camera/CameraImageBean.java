package com.skcodestack.stack.camera;

import android.net.Uri;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class CameraImageBean {
    private Uri mPath;

    private static CameraImageBean INSTANCE = new CameraImageBean();

    private CameraImageBean() {
    }

    public static final CameraImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
