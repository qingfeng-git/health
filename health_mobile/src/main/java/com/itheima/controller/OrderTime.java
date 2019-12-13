package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: OrderTime
 * @Author: QingFeng
 * @Date: 2019-12-13 15:33
 * @Description: //TODO
 */

public class OrderTime {
    @Reference
    private OrderService orderService;

    public void run (){
        //获取到当天日期
        try {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,-1);
            Date time = instance.getTime();
            String string = DateUtils.parseDate2String(time);
            orderService.delet(string);
            System.out.println("删除了"+string);
        }catch (Exception e){

        }

    }

}
