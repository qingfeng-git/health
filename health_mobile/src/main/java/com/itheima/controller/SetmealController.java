package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.List;

/**
 * @PackageName: com.itheima
 * @ClassName: SetmealController
 * @Author: QingFeng
 * @Date: 2019-12-6 11:18
 * @Description: //TODO
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {

       /* //获取redis客户端连接
        String s = jedisPool.getResource().get("setmeal");

        //判断s是否有值
        if (s == null || s.length() < 0) {
            //redis中没有数据
            System.out.println("redis中没数据，查询数据库...");*/
            //从数据库查询
            try {
                List<Setmeal> setmeal = setmealService.getSetmeal();
                //2.2将list序列化为json
               /* List<Setmeal> setmeal1 = jedisPool.getResource().set("setmeal", setmeal);*/
                //将json对象存入到redis中

                return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmeal);
            } catch (Exception e) {
                return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
            }
/*
        } else {
            //从缓存获取
            System.out.println("从缓冲中查询");
            List<Setmeal> list = JSON.parseArray(s, Setmeal.class);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
        }*/
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
