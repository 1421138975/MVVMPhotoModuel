package com.dfzt.network.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseNetwork {

    //这个是Retrofit中需要的一个baseUrl
    protected String BASE_URL;
    //定义一个Map目的是用来缓存Retrofit
    private Map<String, Retrofit> mCacheRetrofit = new HashMap<>();
    //OkHttp
    private OkHttpClient mOkHttpClient;
    protected BaseNetwork(String BASE_URL){
        this.BASE_URL = BASE_URL;
    }

    //获取Retrofit对象
    protected Retrofit getRetrofit(Class cls){
        //1.先判断缓存中是否存在这个数据
        Retrofit mRetrofit = mCacheRetrofit.get(BASE_URL + cls.getName());
        if ( mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    //设置GSON解析
                    .addConverterFactory(GsonConverterFactory.create())
                    //设置返回的适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private OkHttpClient getOkHttpClient(){
        if (mOkHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .writeTimeout(55, TimeUnit.SECONDS);
            //添加拦截器
            if (getInterceptor() != null) {
                builder.addInterceptor(getInterceptor());
            }
            if (getBackInterceptor() != null) {
                builder.addInterceptor(getBackInterceptor());
            }
            mOkHttpClient = builder.build();
        }

        return mOkHttpClient;
    }

    //定义一个抽象方法去获取拦截器(请求的拦截器)
    protected Interceptor getInterceptor(){
        return null;
    }

    //获取返回的拦截器
    protected Interceptor getBackInterceptor(){
        return null;
    }

    //线程切换
    public static <T> ObservableTransformer<T,T> applySchedulers(final Observer<T> observer){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable observable = upstream.subscribeOn(Schedulers.io())//给上面的代码分配线程
                        .observeOn(AndroidSchedulers.mainThread());//给下面的代码分配线程
                observable.subscribe(observer);
                return observable;
            }
        };
    }

}
