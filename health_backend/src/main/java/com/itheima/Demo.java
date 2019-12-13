package com.itheima;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @PackageName: com.itheima
 * @ClassName: Demo
 * @Author: QingFeng
 * @Date: 2019-12-12 13:33
 * @Description: //TODO
 */

public class Demo {
    public static void main(String[] args) {
        //获取到当前时间
        Calendar c = Calendar.getInstance();
        //将每一周的第一天设置为周一
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        Date time = c.getTime();
        SimpleDateFormat a = new SimpleDateFormat("yyyy.MM.dd");
        String format = a.format(time);
        System.out.println(format);
    }
}
