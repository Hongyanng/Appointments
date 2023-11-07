package com.xinke.edu.Appointment.entity;

import java.util.List;


/**
 * 接受服务器端返回结果
 * @param <T>
 */

public class Result<T> {
    public static final int SUCCESS = 200;
    public static final int FAIL = 100;
    int code;//编码：200成功
    String msg;//错误信息
    T data;//数据
    List<T> datas;

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
