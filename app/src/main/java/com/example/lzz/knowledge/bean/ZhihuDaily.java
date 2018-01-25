package com.example.lzz.knowledge.bean;

import java.util.ArrayList;

/**
 * Created by ZZ on 2018/1/18.
 */

public class ZhihuDaily {

    /**
     * date : 20180124
     * stories : [{"images":["https://pic4.zhimg.com/v2-0f0360d86911ebf39ce5910a9912a9eb.jpg"],
     * "type":0,
     * "id":9667369,
     * "ga_prefix":"012422",
     * "title":"小事 · 再也无法直视安检员"}]
     */

    private String date;
    private ArrayList<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(ArrayList<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https://pic4.zhimg.com/v2-0f0360d86911ebf39ce5910a9912a9eb.jpg"]
         * type : 0
         * id : 9667369
         * ga_prefix : 012422
         * title : 小事 · 再也无法直视安检员
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private ArrayList<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }
    }
}
