package com.itg.util_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 存储SharedPreferences
 */
public class SPUtils {

    private SharedPreferences mSp;
    private SharedPreferences.Editor mEdt;

    private static class Holder {
        public static SPUtils SPUtils = new SPUtils();
    }

    public static SPUtils getXml(){
        return Holder.SPUtils;
    }


    /**
     * SharedPreferences对应的文件名
     */
    public SPUtils table(String name) {
        mSp = ToolConfig.app().getSharedPreferences(name, Context.MODE_PRIVATE);
        return this;
    }

    @SuppressLint("CommitPrefEdits")
    public SPUtils startEdit() {
        if (mSp == null) {
            throw new IllegalArgumentException("请先调用 table() 函数");
        }
        mEdt = mSp.edit();
        return this;
    }


    public SPUtils add(String key, Object value) {
        if (mEdt == null) {
            throw new IllegalArgumentException("请先调用 table() 和startEdit() 函数");
        }
        if (value instanceof String) {
            mEdt.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            mEdt.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            mEdt.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            mEdt.putLong(key, (Long) value);
        } else {
            throw new IllegalArgumentException("you argument don't support");
        }
        return this;
    }


    public String getString(String key) {
        return mSp.getString(key, "");
    }

    public float getFloat(String key) {
        return mSp.getFloat(key, -1);
    }

    public int getInt(String key) {
        return mSp.getInt(key, -1);
    }

    public long getLong(String key) {
        return mSp.getLong(key, -1);
    }

    public boolean getBoolean(String key) {
        return mSp.getBoolean(key, false);
    }

    //销毁
    public SPUtils remove(String key) {
        if (mEdt != null) {
            mEdt.remove(key);
        }
        return this;
    }

    public SPUtils clean() {
        if (mEdt != null) {
            mEdt.clear();
        }
        return this;
    }

    public SPUtils commit() {
        if (mEdt != null) {
            mEdt.commit();
        }
        return this;
    }



}
