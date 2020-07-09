package com.dfzt.mvvmphotoactivity.api;

import com.dfzt.network.base.BaseNetwork;

import okhttp3.Interceptor;

public class PhotoApi extends BaseNetwork {
    private PhotoApi() {
        super("http://gank.io/api/");
    }

    private volatile static PhotoApi mPhotoApi;

    public static PhotoApi getInstance(){
        //双重检查
        if (mPhotoApi == null) {
            //1.第一次检查为空
            synchronized (PhotoApi.class) {
                //加了一个对象锁 防止在多线程的时候重复创建（只有本次拿到这个锁的对象执行完成时候 释放了锁，才能由下一个对象进入）
                if (mPhotoApi == null) {
                    //2.这次判断是防止第一个调用者先拿到了锁开始执行  第二个呢已经判断了第一个是为null
                    //     这个时候被阻塞了 这个时候 第一个调用者执行完成 释放了锁 （对象已经创建），
                    //     接下来第二个调用者拿到了锁 开始执行 如果不加这个判断的话 就会重新再次创建一个对象
                    mPhotoApi = new PhotoApi();
                }
            }
        }
        return mPhotoApi;
    }

    //利用泛型返回不同的对象
    public <T> T getServiceApi(Class<T> cls){
        return getInstance().getRetrofit(cls).create(cls);
    }

}
