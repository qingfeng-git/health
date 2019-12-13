package com.itheima.mapper;

import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {




    OrderSetting findByOrderDate(Date parseString2Date);

    void add(Order order);

    Map findById(Integer id);

    Member findBytelephone(String telephone);

    void adds(Member member);

    Integer todayOrderNumber(String reportDate);

    Integer todayVisitsNumber(String reportDate);

    Integer thisWeekOrderNumber(Map<String, String> map);

    Integer thisWeekVisitsNumber(Map<String, String> map);

    Integer thisMonthOrderNumber(Map<String, String> map);

    Integer thisMonthVisitsNumber(Map<String, String> map);

    List<Map<String, Object>> hotSetmeal();

    void delet(String string);
}
