package com.lyj.entity;

/**
 * Created by 陆英杰
 * 2018/9/27 0:35
 */

/**
 * http请求返回的最外层对象
 */
public class Result<T> {

    //错误码
    private Integer code;
    //提示信息
    private String message;
    //具体内容
    private T data;
    //状态
    private String state;

    public Result() {
    }

    public Result(Integer code, String message, T data, String state) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
