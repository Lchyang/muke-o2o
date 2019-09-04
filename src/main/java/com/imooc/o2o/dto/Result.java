package com.imooc.o2o.dto;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {
    private boolean success;
    private T data;
    private String errMsg;
    private int errCode;

    //成功时构造器
    public Result(boolean success, T data){
        this.success = success;
        this.data = data;
    }

    //失败时构造器
    public Result(boolean success, String errMsg, int errCode){
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
