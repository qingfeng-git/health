package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: OrderSettingController
 * @Author: QingFeng
 * @Date: 2019-12-5 13:38
 * @Description: //TODO
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * @return com.itheima.entity.Result
     * @Description //文件上传
     * @Date 2019-12-5 19:43
     * @Param [excelFile]
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0) {
                //传递过来的参数是字符串类型,转换成ordersetting对象格式
                ArrayList<OrderSetting> arrayList = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    arrayList.add(orderSetting);
                }
                orderSettingService.add(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * @return com.itheima.entity.Result
     * @Description //在页面展示数据
     * @Date 2019-12-5 19:42
     * @Param [date]
     **/
    @RequestMapping("/find")
    public Result find(String date) {
        try {
            //返回的list集合中的map  格式为页面需要的格式类型,前台才可以正常解析与赋值
            List<Map> list = orderSettingService.find(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }


    @RequestMapping("/Edit")
    public Result Edit(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}

