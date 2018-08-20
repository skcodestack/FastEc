package com.skcodestack.stack.net.callback;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/12
 * Version  1.0
 * Description:
 */

public interface IProgressListener {
    void onProgress(long totalBytesRead, long l, boolean b);
}
