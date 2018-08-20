package com.skcodestack.stack.camera;

import android.net.Uri;

import com.skcodestack.stack.delegates.PermissionCheckerDelegate;
import com.skcodestack.stack.util.file.FileUtil;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description: 相机调用
 */

public class LemonCamera {

    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMA",",jpg")).getPath());
    }

    public static final void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }

}
