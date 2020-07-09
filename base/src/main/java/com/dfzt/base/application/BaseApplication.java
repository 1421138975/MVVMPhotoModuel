package com.dfzt.base.application;

import android.app.Application;

import com.dfzt.base.loadsir.CustomCallback;
import com.dfzt.base.loadsir.EmptyCallback;
import com.dfzt.base.loadsir.ErrorCallback;
import com.dfzt.base.loadsir.LoadingCallback;
import com.dfzt.base.loadsir.SuccessCallback;
import com.dfzt.base.loadsir.TimeoutCallback;
import com.dfzt.base.util.SharePFTool;
import com.kingja.loadsir.core.LoadSir;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        SharePFTool.init(this);
        //初始化加载库
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new SuccessCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}
