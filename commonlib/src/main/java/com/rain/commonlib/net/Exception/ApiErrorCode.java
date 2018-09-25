package com.rain.commonlib.net.Exception;

/**
 * 和自己后台规定的错误码代码，只要求再onError的时候抛出错误
 * http://www.jianshu.com/p/c105a4177982
 */

interface ApiErrorCode {

    int USERID_NOT_EXIST = 7;//用户未登录或者不存在，userid查询不到
    int  REQUEST_OK= 0;//网络请求成功并获取到数据
    int  SESSION_PAST= 4;//session过期
    int  SERVICE_ERROR= 5;//程序异常

}
