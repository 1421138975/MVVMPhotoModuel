package com.dfzt.mvvmphotoactivity.api;

import com.dfzt.mvvmphotoactivity.bean.PhotoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotoApiInterface {

    @GET("data/%E7%A6%8F%E5%88%A9/{pageSize}/{page}")
    Observable<PhotoBean> getPhotos(@Path("pageSize") int pageSize, @Path("page") int page);
}
