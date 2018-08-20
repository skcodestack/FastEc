package com.skcodestack.stack.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.skcodestack.stack.camera.CameraImageBean;
import com.skcodestack.stack.camera.LemonCamera;
import com.skcodestack.stack.camera.RequestCode;
import com.skcodestack.stack.ui.scanner.ScannerDelegate;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import java.security.Permissions;

import github.com.permissionlib.StonePermission;
import github.com.permissionlib.annotation.PermissionFail;
import github.com.permissionlib.annotation.PermissionSuccess;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */


public abstract class PermissionCheckerDelegate extends BaseDelegate {

    private static final int CAMERA_PERMISSON_REQUEST_CODE = 0X00025;
    private static final int SCAN_PERMISSON_REQUEST_CODE = 0X00026;

    public void startCamera() {
        StonePermission.with(this)
                .addRequestCode(CAMERA_PERMISSON_REQUEST_CODE)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA)
                .request();
    }

    @PermissionSuccess(requestCode = CAMERA_PERMISSON_REQUEST_CODE)
    void onCameraSuccess() {
        LemonCamera.start(this);
    }

    @PermissionFail(requestCode = CAMERA_PERMISSON_REQUEST_CODE)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }


    public void starScan() {
        StonePermission.with(this)
                .addRequestCode(SCAN_PERMISSON_REQUEST_CODE)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @PermissionSuccess(requestCode = SCAN_PERMISSON_REQUEST_CODE)
    void onScanSuccess() {
        getParentDelegate().start(new ScannerDelegate(),RequestCode.SACN);
    }

    @PermissionFail(requestCode = SCAN_PERMISSON_REQUEST_CODE)
    void onScanDenied() {
        Toast.makeText(getContext(), "不允许扫描", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将申请权限处理交给StonePermission
        StonePermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCode.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = LemonCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCode.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.callback(cropUri);
                    }
                    break;
                case RequestCode.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
