package com.skcodestack.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.IError;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class ExampleDelegate extends LemonDelegate {

    @Override
    public Object getLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        textData();
    }

    private void textData() {
        RestClient client = RestClient.builder()
                .url("http:192.168.0.1/index")
                .loader(getActivity())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {
                        Toast.makeText(getActivity(), "error:code:" + code + "--message:" + message, Toast.LENGTH_SHORT).show();
                    }
                })
                .request(new IRequest() {
                    @Override
                    public void onReqestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .build();

        client.get();
    }
}
