package com.rain.commonlib.net.Exception;

/**
 * 自己后台规定的错误码代码，只要求再onError的时候抛出错误
 * 称为业务异常
 * http://www.jianshu.com/p/c105a4177982
 */

public interface ApiErrorCode {

    // 用户未登录或者不存在，userid查询不到
    int USERID_NOT_EXIST = 7;

    // 网络请求成功并获取到数据
    int  REQUEST_OK= 0;

    // session过期
    int  SESSION_PAST= 4;

    // 程序异常
    int  SERVICE_ERROR= 5;

    // 没有网络连接
    int  NET_INTERRUPT= 1;

}
