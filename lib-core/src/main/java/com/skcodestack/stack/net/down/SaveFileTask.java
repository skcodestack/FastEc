package com.skcodestack.stack.net.down;

import android.os.AsyncTask;

import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String dir = (String) params[0];
        String extension = (String)params[1];
        final String name = (String)params[2];
        final ResponseBody responseBody = (ResponseBody) params[3];
        final InputStream is = responseBody.byteStream();

        if(dir == null || "".equals(dir)){
            dir = "default_download_dir";
        }
        if(extension == null || "".equals(extension)){
            extension = "";
        }
        if(name == null){
            return FileUtil.writeToDisk(is,dir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,dir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}
