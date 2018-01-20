package com.example.lzz.knowledge.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/1/18.
 */

public class ZhihuDaily {

    /**
     * date : 20180118
     * stories : [{"images":["https://pic1.zhimg.com/v2-942c551170aa07ffb6eeba787ff5a2d0.jpg"],
     * "type":0,"id":9666833,"ga_prefix":"011821",
     * "title":"有多少人被一句「干得不错」毁了一生？"}]
     * top_stories : [{"image":"https://pic3.zhimg.com/v2-1832f63fa903fda40e7ef83be6d45cce.jpg",
     * "type":0,
     * "id":9666709,
     * "ga_prefix":"011811",
     * "title":"拿着屏幕玩手柄，任天堂把「硬盒」游戏玩出新高度"}]
     */

    private String date;
    private ArrayList<StoriesBean> stories;
    private ArrayList<TopStoriesBean> top_stories;

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

    public ArrayList<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public class StoriesBean {
        /**
         * images : ["https://pic1.zhimg.com/v2-942c551170aa07ffb6eeba787ff5a2d0.jpg"]
         * type : 0
         * id : 9666833
         * ga_prefix : 011821
         * title : 有多少人被一句「干得不错」毁了一生？
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

    public class TopStoriesBean {
        /**
         * image : https://pic3.zhimg.com/v2-1832f63fa903fda40e7ef83be6d45cce.jpg
         * type : 0
         * id : 9666709
         * ga_prefix : 011811
         * title : 拿着屏幕玩手柄，任天堂把「硬盒」游戏玩出新高度
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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
    }
}
