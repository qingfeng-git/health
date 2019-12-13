package com.itheima.mapper;


import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {
    long findCountByOrderDate(Date orderDate);

    void Exdit(OrderSetting setting);

    void add(OrderSetting setting);

    List<OrderSetting> find(Map<String, String> map);

    void Edit(OrderSetting orderSetting);
}
