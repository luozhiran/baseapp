package com.yk.net_lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                .registerTypeAdapter(long.class, new LongDefaultAdapter())
                .registerTypeAdapter(String.class, new StringNullAdapter())
                .create();

    }
}
