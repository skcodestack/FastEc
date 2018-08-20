package com.skcodestack.stack.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/23
 * Version  1.0
 * Description:
 */

public class ScannerDelegate extends LemonDelegate implements ZBarScannerView.ResultHandler {

    ScanView mScannerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScannerView == null) {
            mScannerView = new ScanView(getContext());
        }
        mScannerView.setAutoFocus(true);
    }

    @Override
    public Object getLayout() {
        return mScannerView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();           // Stop camera on pause
        }

    }

    @Override
    public void handleResult(Result result) {
        IGlobalCallback callback = CallbackManager.getInstance().getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.callback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
