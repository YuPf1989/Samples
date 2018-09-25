package com.rain.commonlib.net.MyGsonConverter;

import java.io.Serializable;

/**
 * 基础的数据格式形式
 *http://www.jianshu.com/p/c105a4177982
 *
 */

public class Result<T> implements Serializable {

    /**
     * 判断错误码是否为0,0表示获取数据ok
     * @return
     */
    public boolean isOk() {
        return error_code == 0;
    }

    private  int  error_code;

    private String msg;

    private T data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
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
