package com.cheapest.lansu.cheapestshopping.model.entity;

import com.cheapest.lansu.cheapestshopping.Constant.ErrorCode;

/*
* 文件名：BaseEntity
* 描    述：基础数据类型 拦截返回码
* 作    者：lansu
* 时    间：2018/4/26 12:07
* 版    权：
*/
public class BaseEntity<T> {
    private static int SUCCESS_CODE=0;//成功的code
    private int code;
    private String msg;
    private T data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public boolean isTokenLose(){
        return getCode()== ErrorCode.Forced_Logoff;
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

}