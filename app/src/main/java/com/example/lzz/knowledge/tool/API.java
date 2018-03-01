package com.example.lzz.knowledge.tool;

/**
 * Created by ZZ on 2018/1/17.
 */

public class API {

    // 最新消息
    public static final String ZHIHU_NEWS_LATEST = "http://news-at.zhihu.com/api/4/news/latest";

    // 消息内容获取与离线下载
    // 在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容
    public static final String ZHIHU_NEWS_DETAIL = "http://news-at.zhihu.com/api/4/news/";

    // 过往消息
    // 若要查询的11月18日的消息，before后面的数字应该为20161119
    // 知乎日报的生日为2013 年 5 月 19 日，如果before后面的数字小于20130520，那么只能获取到空消息
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";

    // 妹纸图片
    // http://gank.io/api/data/福利/请求个数/第几页
    public static final String MEIZHI = "http://gank.io/api/data/福利/10/";
}
