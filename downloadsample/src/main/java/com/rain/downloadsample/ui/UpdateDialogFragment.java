package com.rain.downloadsample.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.rain.downloadsample.R;

/**
 * Author:rain
 * Date:2018/9/14 9:41
 * Description:
 * 更新进度的dialog
 * 顺便练习下dialogfragment
 * 参见：https://blog.csdn.net/u013855006/article/details/79841836
 * 方式一：在onCreatDialog中实现
 * 
 */
public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {

    private FragmentActivity mActivity;
    public ProgressBar pb;
    public NumberProgressBar pb2;
    private Button btnUpdate;
    private StartDownloadListener startDownloadListener;

    public static UpdateDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
//        Dialog dialog = new Dialog(mActivity, R.style.bran_online_supervise_dialog);
        Dialog dialog = new Dialog(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_updatedialog, null);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
//        pb = view.findViewById(R.id.pb);
        pb2 = view.findViewById(R.id.pb2);
        dialog.setTitle("更新");
        dialog.setContentView(view);
        dialog.setCancelable(false);
        // 点击外部不消失
        dialog.setCanceledOnTouchOutside(false);

        // 设置dialog的弹出位置
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置dialog的宽高
        WindowManager windowManager = window.getWindowManager();
        WindowManager.LayoutParams p = window.getAttributes();
        // 获取屏幕宽高用
        Display display = windowManager.getDefaultDisplay();
//        p.height = (int) (display.getHeight() * 0.6);
        p.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(p);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update) {
            btnUpdate.setVisibility(View.GONE);
            pb2.setVisibility(View.VISIBLE);
            if (startDownloadListener != null) {
                startDownloadListener.startDownload();
            }
        }
    }

    public interface StartDownloadListener {
        void startDownload();
    }

    public UpdateDialogFragment setStartDownloadListener(StartDownloadListener startDownloadListener) {
        this.startDownloadListener = startDownloadListener;
        return this;
    }
}
