package com.Floating_Cloud.Event_Point;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable t);
}