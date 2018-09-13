package com.rain.downloadsample.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author:rain
 * Date:2018/9/13 11:21
 * Description:
 */
public interface DownloadService {
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);

}
