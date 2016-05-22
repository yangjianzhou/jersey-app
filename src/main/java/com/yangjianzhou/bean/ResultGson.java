package com.yangjianzhou.bean;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public class ResultGson<T> {

    private String resCode ;

    private String resMsg ;

    private T data ;


    public ResultGson(String resCode, String resMsg, T data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.data = data;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
