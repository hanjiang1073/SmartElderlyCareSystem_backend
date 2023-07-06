package com.example.crud3.utils;

public class ReturnObject<T> {
    public T getObject() {
        return obj;
    }

    public void setObject(T object) {
        this.obj = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private T obj;
    public ReturnObject(int code, T object)
    {
        this.obj = object;
        this.code = code;
    }

}
