package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @PackageName: com.itheima.impl
 *
 * @ClassName: OrderSettingServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-5 17:00
 * @Description: //TODO
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;


    /**
     * @Description //预约界面的显示
     * @Date 2019-12-5 18:14
     * @Param [arrayList]
     * @return void
     **/
    public void add(ArrayList<OrderSetting> arrayList) {
            if (arrayList != null && arrayList.size()>0){
                //遍历集合,到得每一个OrderSetting对象
                for (OrderSetting setting : arrayList) {
                  long count = orderSettingMapper.findCountByOrderDate(setting.getOrderDate());
                    //首先判断数据库中是否是否存在相同的日期信息,存在就修改,不存在就添加
                    if (count > 0){
                        //存在
                        orderSettingMapper.Exdit(setting);
                    }else {
                        //不存在
                        orderSettingMapper.add(setting);
                    }
                }
            }else {
                throw new RuntimeException();
            }
    }

    @Override
    public List<Map> find(String date) {
        //定义字符串,将数据库中查询语句改变格式
        String begin = date + "-1";
        String end = date +"-31";
        //将条件封装进map集合中,做为参数带到mapper文件中
        Map<String, String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //调用方法进行数据库的查询
        List<OrderSetting> list =  orderSettingMapper.find(map);
        //遍历集合
        Calendar instance = Calendar.getInstance();
        //新建一个集合,用于封装前台需要的数据类型进行返回给前台页面
        List<Map> list1 = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map map1 = new HashMap<>();
            instance.setTime(orderSetting.getOrderDate());

            map1.put("date",instance.get(Calendar.DAY_OF_MONTH));
            map1.put("number",orderSetting.getNumber());
            map1.put("reservations",orderSetting.getReservations());
            list1.add(map1);
        }
        return list1;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            orderSettingMapper.Exdit(orderSetting);
        }else {
            orderSettingMapper.add(orderSetting);
        }
    }
}
