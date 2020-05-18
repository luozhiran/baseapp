package com.yk.base.utils;

import android.content.Context;

import java.util.HashMap;

/**
 * 把数据存储在内容缓存中，慎用
 * 该类是单例类，存储数据在里面后，会一直被保存在内存中，直到使用者手动释放
 */
public class StaticMemoryCache {

    /**
     * Context 是Application
     */
    private Context mContext;

    static class Holder {
        private static StaticMemoryCache memoryCache = new StaticMemoryCache();
    }


    public static StaticMemoryCache cache() {
        return Holder.memoryCache;
    }

    public void register(Context context) {
        mContext = context;
    }


    /*****************************存储逻辑********************************************/

    private HashMap<String, Object> mCache;//一直保存在内存中

    public StaticMemoryCache() {
        mCache = new HashMap<>();
    }

    public void addCache(String key, Object object) {
        mCache.put(key, object);
    }

    public <T> T getCache(String key, T t) {
        Object o = mCache.remove(key);
        if (o == null) {
            return t;
        } else {
            return (T) o;
        }
    }
}
