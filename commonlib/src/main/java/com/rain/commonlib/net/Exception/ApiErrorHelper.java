package com.rain.commonlib.net.Exception;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.net.ConnectException;


/**
 * 统一错误的处理类
 *
 * http://www.jianshu.com/p/c105a4177982
 */

public class ApiErrorHelper {

    private static final String TAG  = ApiErrorHelper.class.getSimpleName();


    public static void handleCommonError(Context context, Throwable e) {

        Log.e(TAG, "handleCommonError: e"+e.toString());

        if (e instanceof ConnectException) {    // 网络连接错误
            Toast.makeText(context, "网络连接异常！", Toast.LENGTH_SHORT).show();

        } else if (e instanceof JSONException) {    // gson数据转换错误

            Toast.makeText(context, "json数据异常！", Toast.LENGTH_SHORT).show();

        } else if (e instanceof ApiException) {     // 后台定义的错误
            // ApiException处理
            ApiException exception=(ApiException)e;
            Toast.makeText(context, "error_code:"+exception.getErrorCode()+";"+"msg:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "服务器开小差了！", Toast.LENGTH_SHORT).show();
        }
    }
}


