package com.live.api;

public interface Callback<T> {
    void fail(String msg);

    void success(T t);
}
