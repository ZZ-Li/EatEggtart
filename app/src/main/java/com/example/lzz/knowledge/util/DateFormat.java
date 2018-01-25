package com.example.lzz.knowledge.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 2018/1/25.
 */

public class DateFormat {

    /**
     * 将long类date转换为String类型
     * @param date date
     * @return String date
     */
    public String ZhihuDailyDateFormat(long date){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(d);
    }
}
