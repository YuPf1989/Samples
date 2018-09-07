package com.rain.qrcodesample;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView.Delegate;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Author:rain
 * Date:2018/9/6 11:59
 * Description:
 */
public class ZxingScanActivity extends AppCompatActivity implements Delegate, View.OnClickListener {
    private static final String TAG = "ScanActivity";
    private ZXingView mZXingView;
    private boolean open_flashlight = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_scan);
        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
        findViewById(R.id.open_flashlight).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        // 延迟0.5秒后开始识别
        mZXingView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_flashlight:
                handleFlashLight();
                break;

            default:
                break;
        }

    }

    private void handleFlashLight() {
        open_flashlight = !open_flashlight;
        if (open_flashlight) {
            mZXingView.openFlashlight();
        } else {
            mZXingView.closeFlashlight();
        }
    }
}
