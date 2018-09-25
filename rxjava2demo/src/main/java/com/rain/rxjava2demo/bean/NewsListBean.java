package com.rain.rxjava2demo.bean;

import java.util.List;

/**
 * Author:rain
 * Date:2017/7/22 9:48
 * Description:
 */

public class NewsListBean {


    private List<NewsListBean1> newsList;

    public List<NewsListBean1> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListBean1> newsList) {
        this.newsList = newsList;
    }

    public static class NewsListBean1 {
        /**
         * news_id : 28f7caefedda4ca984b485073f3d2744
         * title : 习近平十个比喻描绘“一带一路”蓝图
         * digest : 央视网消息：世界历史，卷帙浩繁，化繁为简，是为妙喻。

         * time : 2017-05-09 14:46:34
         */

        private String news_id;
        private String title;
        private String digest;
        private String time;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "NewsListBean1{" +
                    "news_id='" + news_id + '\'' +
                    ", title='" + title + '\'' +
                    ", digest='" + digest + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsListBean{" +
                "newsList=" + newsList +
                '}';
    }
}
