package com.example.lzz.knowledge.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/1/18.
 */

public class ZhihuDaily {

    /**
     * date : 20180118
     * stories : [{"images":["https://pic1.zhimg.com/v2-942c551170aa07ffb6eeba787ff5a2d0.jpg"],"type":0,"id":9666833,"ga_prefix":"011821","title":"有多少人被一句「干得不错」毁了一生？"},{"images":["https://pic3.zhimg.com/v2-b7f7b9abab20a1c625c0db30d564949e.jpg"],"type":0,"id":9666451,"ga_prefix":"011819","title":"结婚后，你发现自己找错了人......"},{"images":["https://pic4.zhimg.com/v2-9afff2d66235ab6a1e526e1d73cf75f7.jpg"],"type":0,"id":9666817,"ga_prefix":"011818","title":"长期不上班是种怎样的体验？"},{"title":"羽绒服怎么搭配，能保暖又显瘦？","ga_prefix":"011818","images":["https://pic2.zhimg.com/v2-fa7971af7f8b19abcb599dbc901005ad.jpg"],"multipic":true,"type":0,"id":9666626},{"images":["https://pic4.zhimg.com/v2-4d57943c447c2f4676c4f62b349654d7.jpg"],"type":0,"id":9666755,"ga_prefix":"011816","title":"山东的 GDP 这么高，为什么还被说穷？"},{"images":["https://pic3.zhimg.com/v2-5f65cdb49d17e248b3bb0a23fde10a6a.jpg"],"type":0,"id":9666750,"ga_prefix":"011815","title":"中国女子大闹中国机场：「言论自由，国外都这样」，呃\u2026\u2026"},{"images":["https://pic1.zhimg.com/v2-38c5e0b5396c3222cec14c6bab7c0448.jpg"],"type":0,"id":9666603,"ga_prefix":"011812","title":"大误 · 同学，你也开发火星啊？"},{"images":["https://pic3.zhimg.com/v2-4c05bb7594c51a53c15b3b21c833f5ca.jpg"],"type":0,"id":9666709,"ga_prefix":"011811","title":"拿着屏幕玩手柄，任天堂把「硬盒」游戏玩出新高度"},{"images":["https://pic4.zhimg.com/v2-6eee2eb37ca31181289dc5af0add834f.jpg"],"type":0,"id":9666381,"ga_prefix":"011810","title":"都是拍片子，X 光、CT、B 超、核磁共振......有什么区别？"},{"images":["https://pic3.zhimg.com/v2-6412410a5a2b264601e5f4829579e4c6.jpg"],"type":0,"id":9666161,"ga_prefix":"011809","title":"一打开窗全是「墓地」，买到这种房，恐怕告开发商也难赢"},{"images":["https://pic4.zhimg.com/v2-f61287a33049c3240da3e18e83baeaaf.jpg"],"type":0,"id":9666463,"ga_prefix":"011808","title":"为什么火车硬座不像公交车那样，非要人面对面坐？"},{"title":"生活在硅谷，我来讲讲这里的日常是怎样的","ga_prefix":"011807","images":["https://pic2.zhimg.com/v2-0999dc7eab7461a8542611d127fb42f9.jpg"],"multipic":true,"type":0,"id":9666437},{"images":["https://pic1.zhimg.com/v2-1fb5c85cb7dcfaf55b20b5300f901154.jpg"],"type":0,"id":9666634,"ga_prefix":"011807","title":"- 有哪些外行觉得很蠢，实际设计很精妙的东西？\r\n- 验证码"},{"images":["https://pic3.zhimg.com/v2-66b0c47b3d1b27c1eb5ff937b27e6d22.jpg"],"type":0,"id":9666616,"ga_prefix":"011807","title":"深访「币圈」：享受过一夜暴富，你就再也忘不掉那种捷径"},{"images":["https://pic1.zhimg.com/v2-ebc6042968339b381cf3b7d5c9e63224.jpg"],"type":0,"id":9666642,"ga_prefix":"011806","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic3.zhimg.com/v2-1832f63fa903fda40e7ef83be6d45cce.jpg","type":0,"id":9666709,"ga_prefix":"011811","title":"拿着屏幕玩手柄，任天堂把「硬盒」游戏玩出新高度"},{"image":"https://pic1.zhimg.com/v2-08cd73a1e2bf42a19d6d634a4f422de8.jpg","type":0,"id":9666616,"ga_prefix":"011807","title":"深访「币圈」：享受过一夜暴富，你就再也忘不掉那种捷径"},{"image":"https://pic4.zhimg.com/v2-898fb75c59478e8665b0473328c4d03f.jpg","type":0,"id":9666463,"ga_prefix":"011808","title":"为什么火车硬座不像公交车那样，非要人面对面坐？"},{"image":"https://pic2.zhimg.com/v2-d5d35a8d4b77cd9535575b9cb91f360d.jpg","type":0,"id":9666437,"ga_prefix":"011807","title":"生活在硅谷，我来讲讲这里的日常是怎样的"},{"image":"https://pic4.zhimg.com/v2-1e173d65bfa662cc370e35ce8578458f.jpg","type":0,"id":9666161,"ga_prefix":"011809","title":"一打开窗全是「墓地」，买到这种房，恐怕告开发商也难赢"}]
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
