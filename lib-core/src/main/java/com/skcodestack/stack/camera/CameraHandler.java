package com.skcodestack.stack.camera;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.skcodestack.stack.R;
import com.skcodestack.stack.delegates.PermissionCheckerDelegate;
import com.skcodestack.stack.util.file.FileUtil;

import java.io.File;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:相机处理类
 */

public class CameraHandler implements View.OnClickListener {

    private final AlertDialog ALERTDIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHandler( PermissionCheckerDelegate delegate) {
        this.DELEGATE = delegate;
        this.ALERTDIALOG = new AlertDialog.Builder(delegate.getContext()).create();
    }


    public void beginCameraDialog() {
        ALERTDIALOG.show();
        Window window =
                ALERTDIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panle);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams layoutParams =
                    window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(layoutParams);

            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_btn_take) {
            taskPhoto();
            ALERTDIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            ALERTDIALOG.cancel();
        } else if (id == R.id.photodialog_btn_cancel) {
            ALERTDIALOG.cancel();
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private void taskPhoto() {
        String photoName = getPhotoName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File temFile = new File(FileUtil.CAMERA_PHOTO_DIR, photoName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0SDK 以上
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, temFile.getPath());
            ContentResolver resolver =
                    DELEGATE.getContext().getContentResolver();

            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //uri转真实路径
            String realFilePath = FileUtil.getRealFilePath(DELEGATE.getContext(), uri);
            File realfile = FileUtils.getFileByPath(realFilePath);
            Uri realUri = Uri.fromFile(realfile);

            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }else {
            Uri uri = Uri.fromFile(temFile);
            CameraImageBean.getInstance().setPath(uri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }
        DELEGATE.startActivityForResult(intent,RequestCode.TAKE_PHOTO);
    }

    private void pickPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGATE.startActivityForResult(Intent.createChooser(intent,"选择获取图片方式"),RequestCode.PICK_PHOTO);
    }

}
