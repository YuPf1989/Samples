package com.rain.rxjava2demo.bean;

import java.util.List;

/**
 * Author:rain
 * Date:2018/9/27 10:45
 * Description:
 */
public class BaseBean {

    // 对应于test3
    /**
     * message : 快递公司参数异常：单号不存在或者已经过期
     * nu :
     * ischeck : 0
     * condition :
     * com :
     * state : 0
     * data : []
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String state;
    private List<?> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "message='" + message + '\'' +
                ", nu='" + nu + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", condition='" + condition + '\'' +
                ", com='" + com + '\'' +
                ", state='" + state + '\'' +
                ", data=" + data +
                '}';
    }
}
