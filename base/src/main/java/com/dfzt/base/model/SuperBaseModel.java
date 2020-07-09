package com.dfzt.base.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.dfzt.base.util.GsonUtils;
import com.dfzt.base.util.SharePFTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 这个项目是使用RXjava + Retortfit + Okhttp来实现的网络请求
 * @param <T> 就是一个返回的javaBean数据
 */
public abstract class SuperBaseModel<T> {
    //创建主线程的handler 通过来来发送网络请求返回结果
    protected Handler mUiHandler = new Handler(Looper.myLooper());//Looper.myLooper()

    //定义一个RxJava的请求的监听 这个就是用来取消这次网络请求（场景：当前Activity/Fragment已经退出了
    //          网络请求还在执行,这个时候拿到了数据就会返回到对应的Activity/Fragment 就会出问题） 就可以通过mCompositeDisposable.dispose(); 取消网络请求
    private CompositeDisposable mCompositeDisposable;

    //定一个一个引用队列 用来保存每次调用的Listener
    private ReferenceQueue<IModelListener> mReferenceQueue;

    //定义一个弱引用队列 也去保存IModelListener
    protected ConcurrentLinkedQueue<WeakReference<IModelListener>> mWeakListenerArrayList;

    //缓存的数据
    protected BaseCachedData<T> mData;

    public SuperBaseModel(){
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        if (getCachedPreferenceKey() != null){
            //表示子类告诉你 我需要缓存数据
            mData = new BaseCachedData<>();
        }
    }

    //注册监听
    public void register(IModelListener mListener){
        if (mListener == null){
            //如果监听为null的话就不去处理
            return;
        }
        synchronized (this){
            //每次注册的时候清理被系统回收的对象
            Reference<? extends IModelListener> releaseListener = null;
            while ((releaseListener = mReferenceQueue.poll()) != null){
                //如果获取的队列中的数据不为null 就移除弱引用中的数据
                mWeakListenerArrayList.remove(releaseListener);
            }
            //遍历集合
            for (WeakReference<IModelListener> weakListener : mWeakListenerArrayList) {
                IModelListener listenerItem = weakListener.get();
                if (listenerItem == mListener) {
                    return;
                }
            }

            WeakReference<IModelListener> weakListener = new WeakReference<>(mListener,mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }
    }

    //取消注册监听
    public void unRegister(IModelListener mListener){
        if (mListener == null){
            return;
        }
        synchronized (this) {
            //遍历集合里面的监听 然后移除和这个监听一样的数据
            for (WeakReference<IModelListener> weakListener : mWeakListenerArrayList) {
                IModelListener listenerItem = weakListener.get();
                if (mListener == listenerItem) {
                    mWeakListenerArrayList.remove(weakListener);
                    break;
                }
            }
        }
    }
    /**
     * 判断这个model是否要缓存数据,需要的话 填入缓存的key 交给子类完成(由子类完成)
     */
    protected String getCachedPreferenceKey(){
        return null;
    }

    //获取缓存数据的类型(由子类完成)
    protected Type getTClass(){
        return null;
    }

    //保存数据到本地
    protected void saveDataToPreference(T data){
        mData.data = data;//保存的数据
        mData.time = System.currentTimeMillis();//获取当前的时间戳
        //将dataBean转为JSON字符串
        SharePFTool.getInstance().putStringValue(getCachedPreferenceKey(), GsonUtils.toJson(mData));
    }

    /** 是否更新数据，可以在这里设计策略，可以是一天一次，一月一次等等，
     *  默认是每次请求都更新
     * */
    protected boolean isNeedToUpdate() {
        return true;
    }

    //获取缓存的数据和加载
    protected void getCachedDataAndLoad(){
        //1.判断是否需要需要保存数据
        if (getCachedPreferenceKey() != null){
            //表示需要保存 这个时候就可以去获取保存的数据了
            String mCacheData = SharePFTool.getInstance().getStringValue(getCachedPreferenceKey());//获取字符串
            if (!TextUtils.isEmpty(mCacheData)){
                //表示缓存的数据不为空
                //将取出来的数据通过GOSN解析转化为 T
                try {
                    T data = GsonUtils.fromLocalJson(new JSONObject(mCacheData).getString("data"),getTClass());
                    notifyCachedData(data);
                    if (isNeedToUpdate()){
                        //更新数据的逻辑 获取最新的数据
                        loadData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                //保存数据为null 获取最新的数据
                loadData();
            }
        }else {
            //不需要保存数据 每次都去获取最新的数据
            loadData();
        }
    }

    //通知数据改变
    protected abstract void notifyCachedData(T data);

    //加载数据
    protected abstract void loadData();

    //添加RXJava的请求
    public void addDisposable(Disposable d){
        if (d == null) {
            return;
        }

        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(d);
    }

    //取消RxJava的请求
    public void cancel(){
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }



    public interface IModelListener{

    }


}
