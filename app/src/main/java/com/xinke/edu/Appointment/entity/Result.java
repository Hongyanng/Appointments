package com.xinke.edu.Appointment.entity;

import java.util.List;


/**
 * 接受服务器端返回结果
 *
 * @param <T>
 */

public class Result<T> {
    public static final int SUCCESS = 200;
    public static final int FAIL = 201;
    int code;//编码：200成功
    String msg;//错误信息
    /*用户信息的数据*/
    T data;
    List<T> datas;


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

}
