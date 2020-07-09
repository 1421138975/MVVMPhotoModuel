package com.dfzt.base.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Administrator on 2017/5/11.
 * 轻量级数据存储的工具类
 */

public class SharePFTool {
    public static String SPF_NAME = "MVVM_SPF";
    private volatile static SharePFTool sharePFTool = null;
    private SharePFTool(){};
    private static SharedPreferences spf = null;

    public static SharePFTool getInstance(){
        if (sharePFTool == null) {
            synchronized (SharePFTool.class) {
                if (sharePFTool == null) {
                    sharePFTool = new SharePFTool();
                }
            }
        }
        return sharePFTool;
    }

    public static void init(Context context){
        spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
    }

    public String getStringValue(String key){
        if (spf != null){
            return spf.getString(key,"");
        }
        return null;
    }

    public String getStringValue(String key, String dValue){
        if (spf != null){
            return spf.getString(key,dValue);
        }
        return null;
    }


    public void putStringValue(String key, String value){
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public void putIntValue(String key, int value){
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public Integer getIntValue(String key, int value){
        if (spf != null){
            return spf.getInt(key,value);
        }
        return null;
    }

    public void putBooleanValue(String key, boolean value){
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBooleanValue(String key, boolean value){
        if (spf != null){
            return spf.getBoolean(key,value);
        }
        return false;
    }

    public void putLongValue(String key, long value){
        SharedPreferences.Editor editor = spf.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public Long getLongValue(String key, long value){
        if (spf != null){
            return spf.getLong(key,value);
        }
        return null;
    }

    public void removeSPFKey(String key){
        SharedPreferences.Editor editor = spf.edit();
        editor.remove(key);
        editor.apply();
    }

}

