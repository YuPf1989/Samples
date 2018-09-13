package com.rain.commonlib.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Author:rain
 * Date:2018/9/11 15:37
 * Description:
 * 检查app更新遇到的一些权限问题
 */
public class CheckPermissionUtil {
    private Activity activity;

    public CheckPermissionUtil(Activity activity) {
        this.activity = activity;
    }

    // 检查读写权限
    @SuppressLint("CheckResult")
    public boolean checkSDCardPermission() {
        final boolean[] hasSDCardPermission = {false};
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            hasSDCardPermission[0] = aBoolean;
                        }
                    })
        ;
        return hasSDCardPermission[0];
    }
}
