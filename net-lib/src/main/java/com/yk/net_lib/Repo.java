package com.yk.net_lib;

import com.google.gson.annotations.SerializedName;

public class Repo<T> {
    public int code = -1000;
    public String msg;
    public boolean fail;
    public boolean success;
    public T data;
    public BusinessData businessData;
}
