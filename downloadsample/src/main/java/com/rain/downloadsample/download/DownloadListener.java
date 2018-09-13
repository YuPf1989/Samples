package com.rain.downloadsample.download;

/**
 * Author:rain
 * Date:2018/9/13 11:20
 * Description:
 */
public interface DownloadListener {
    void onStart();//下载开始

    void onProgress(int progress);//下载进度

    void onFinish(String path);//下载完成

    void onFail(String errorInfo);//下载失败
}
