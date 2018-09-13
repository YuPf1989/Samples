package com.rain.downloadsample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rain.commonlib.util.CheckPermissionUtil;
import com.rain.downloadsample.download.DownloadListener;
import com.rain.downloadsample.download.DownloadUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String url = "http://140.143.153.125:8080/20180911114244_3024.apk";
    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloadSample/" + "sample.apk";
    private CheckPermissionUtil permissionUtil;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_download).setOnClickListener(this);
        pb = findViewById(R.id.pb);
        pb.setMax(100);

        permissionUtil = new CheckPermissionUtil(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                startDownload();

                break;
        }
    }

    private void startDownload() {
        if (permissionUtil.checkSDCardPermission()) {

            DownloadUtil.download(url, path, new DownloadListener() {
                // 注意全都是在子线程中运行
                @Override
                public void onStart() {
                    Log.e(TAG, "onStart: ");
                }

                @Override
                public void onProgress(int progress) {
                    pb.setProgress(progress);
                    Log.e(TAG, "onProgress: " + progress);
                }

                @Override
                public void onFinish(String path) {
                    Log.e(TAG, "onFinish: ");
                }

                @Override
                public void onFail(String errorInfo) {
                    Log.e(TAG, "onFail: ");
                }
            });

        } else {
            Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
        }

    }
}
