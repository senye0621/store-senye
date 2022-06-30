package com.senye.storesenye.util;

import java.io.Serializable;

/**
 * 响应结果类
 * @param <E> 响应数据类型
 */
public class JsonResult<E> implements Serializable {

    /**响应状态码*/
    private Integer state;

    /**响应描述信息*/
    private String message;

    /**响应数据*/
    private E data;

    public JsonResult() {

    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(E data) {
        this.data = data;
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
