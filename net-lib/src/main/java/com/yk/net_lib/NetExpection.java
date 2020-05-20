package com.yk.net_lib;

import android.widget.Toast;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

public class NetExpection {
    private static final String CONNECT_ERROR = "网络连接失败,请检查网络";
    private static final String CONNECT_TIMEOUT = "连接超时,请稍后再试";
    private static final String BAD_NETWORK = "服务器异常";
    private static final String PARSE_ERROR = "解析服务器响应数据失败";
    private static final String UNKNOWN_ERROR = "未知错误";
    private static final String RESPONSE_RETURN_ERROR = "服务器返回数据失败";

    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }

    private static void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                show(CONNECT_ERROR);
                break;

            case CONNECT_TIMEOUT:
                show(CONNECT_TIMEOUT);
                break;
            case BAD_NETWORK:
                show(BAD_NETWORK);
                break;
            case PARSE_ERROR:
                show(PARSE_ERROR);
                break;
            case UNKNOWN_ERROR:
            default:
                show(UNKNOWN_ERROR);
                break;
        }
    }

    private static void show(String msg){
        if (NetInit.app()!=null) {
            Toast.makeText(NetInit.app(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 网络异常提示
     * @param throwable
     */
    public static void NetExceptionTrip(Throwable throwable) {
        if (throwable instanceof retrofit2.HttpException) {
            //HTTP错误
            onException(NetExpection.ExceptionReason.BAD_NETWORK);
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            //连接错误
            onException(NetExpection.ExceptionReason.CONNECT_ERROR);
        } else if (throwable instanceof InterruptedIOException) {
            //连接超时
            onException(NetExpection.ExceptionReason.CONNECT_TIMEOUT);
        } else if (throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof ParseException) {
            //解析错误
            onException(NetExpection.ExceptionReason.PARSE_ERROR);
        } else {
            //其他错误
            onException(NetExpection.ExceptionReason.UNKNOWN_ERROR);
        }
    }

}
