package com.skcodestack.stack.util.callback;

import java.lang.ref.PhantomReference;
import java.util.WeakHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class CallbackManager {
    private static final WeakHashMap<Object, IGlobalCallback> WEAK_HASH_MAP = new WeakHashMap<>();

    private static final class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    private CallbackManager() {
    }

    public static final CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object key, IGlobalCallback value) {
        WEAK_HASH_MAP.put(key, value);
        return this;
    }

    public IGlobalCallback getCallback(Object key) {
        return WEAK_HASH_MAP.get(key);
    }

}
