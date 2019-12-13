package com.itheima.service;

import com.itheima.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void add(ArrayList<OrderSetting> arrayList);

    List<Map> find(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
