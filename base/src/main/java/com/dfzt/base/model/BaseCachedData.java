package com.dfzt.base.model;

import java.io.Serializable;

public class BaseCachedData<T> implements Serializable {
    public T data;//缓存的类型
    public Long time;//缓存的时间戳
}
