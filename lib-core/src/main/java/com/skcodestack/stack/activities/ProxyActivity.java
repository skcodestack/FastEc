package com.skcodestack.stack.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.skcodestack.stack.R;
import com.skcodestack.stack.delegates.LemonDelegate;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract SupportFragment getRootDelegate();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        ContentFrameLayout content  = new ContentFrameLayout(this);
        content.setId(R.id.delegate_container);
        setContentView(content);
        if(savedInstanceState == null){
            loadRootFragment(R.id.delegate_container,getRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
