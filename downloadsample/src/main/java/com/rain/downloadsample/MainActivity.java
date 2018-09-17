package com.rain.downloadsample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rain.commonlib.util.CheckPermissionUtil;
import com.rain.downloadsample.download.DownloadListener;
import com.rain.downloadsample.download.DownloadUtil;
import com.rain.downloadsample.ui.NumberProgressBar;
import com.rain.downloadsample.ui.UpdateDialogFragment;

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
        findViewById(R.id.btn_showdialog).setOnClickListener(this);
        pb = findViewById(R.id.pb);
        pb.setMax(100);

        permissionUtil = new CheckPermissionUtil(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
//                startDownload();
                showDialog();
                break;

            case R.id.btn_showdialog:
                showDialog2();
                break;
        }
    }

    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.layout_updatedialog)
                .setTitle("更新")
                .setPositiveButton("是", null)
                .setNegativeButton("否", null)
                .create();
        AlertDialog alertDialog = builder.show();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_updatedialog, null);
        Button btnUpdate = view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "更新", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setContentView(view);
    }


    /**
     * AlertDialog分为三个部分，标题，内容区，底部button按键区
     * setView对应的是内容区
     * setContentView对应的是整个窗体，并且必须在show之后调用才会起作用
     * 处理点击dialogPositiveButton不消失的方法
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_updatedialog2, null);
        final NumberProgressBar pb2 = view.findViewById(R.id.pb2);
        builder.setView(view)
                .setTitle("更新")
                .setPositiveButton("是", null) // 先设置为null
                .setNegativeButton("否", null)
                .create();
        final AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击我是不会消失的", Toast.LENGTH_SHORT).show();
                pb2.setVisibility(View.VISIBLE);
                if (permissionUtil.checkSDCardPermission()) {
                    DownloadUtil.download(url, path, new DownloadListener() {
                        // 注意全都是在子线程中运行
                        @Override
                        public void onStart() {
                            Log.e(TAG, "onStart: ");
                        }

                        @Override
                        public void onProgress(final int progress) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb2.setProgress(progress);
                                }
                            });
                            Log.e(TAG, "onProgress: " + progress);
                        }

                        @Override
                        public void onFinish(String path) {
                            Log.e(TAG, "onFinish: ");
                            dialog.dismiss();
                        }

                        @Override
                        public void onFail(String errorInfo) {
                            Log.e(TAG, "onFail: ");
                            dialog.dismiss();
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void startDownload() {
        final UpdateDialogFragment updateDialogFragment = UpdateDialogFragment.newInstance();
        updateDialogFragment.show(getSupportFragmentManager(), "dialog");
        updateDialogFragment.setStartDownloadListener(new UpdateDialogFragment.StartDownloadListener() {
            @Override
            public void startDownload() {
                if (permissionUtil.checkSDCardPermission()) {

                    DownloadUtil.download(url, path, new DownloadListener() {
                        // 注意全都是在子线程中运行
                        @Override
                        public void onStart() {
                            Log.e(TAG, "onStart: ");
                        }

                        @Override
                        public void onProgress(final int progress) {
//                            pb.setProgress(progress);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDialogFragment.pb2.setProgress(progress);
                                }
                            });
                            Log.e(TAG, "onProgress: " + progress);
                        }

                        @Override
                        public void onFinish(String path) {
                            Log.e(TAG, "onFinish: ");
                            updateDialogFragment.dismiss();
                        }

                        @Override
                        public void onFail(String errorInfo) {
                            Log.e(TAG, "onFail: ");
                            updateDialogFragment.dismiss();
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
