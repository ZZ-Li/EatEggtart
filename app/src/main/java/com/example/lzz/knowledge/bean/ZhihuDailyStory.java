package com.example.lzz.knowledge.bean;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/1/22.
 */

public class ZhihuDailyStory {

    /**
     * body : <div class="main-wrap content-wrap">...</div><script type=“text/javascript”>window.daily=true</script>
     * image_source : 《朗读者》
     * title : 为什么我书读得越多，越看不起周围的人？
     * image : https://pic1.zhimg.com/v2-2523da59221bdca2c937daa8d8e5c148.jpg
     * share_url : http://daily.zhihu.com/story/9667235
     * js : []
     * ga_prefix : 012215
     * images : ["https://pic1.zhimg.com/v2-bb6a5d49b03c0df9f0b9c48d375097d4.jpg"]
     * type : 0
     * id : 9667235
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private ArrayList<?> js;
    private ArrayList<String> images;
    private ArrayList<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
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

    public ArrayList<?> getJs() {
        return js;
    }

    public void setJs(ArrayList<?> js) {
        this.js = js;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }
}
