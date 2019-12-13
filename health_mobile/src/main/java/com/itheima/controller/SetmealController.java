package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
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

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmeal = setmealService.getSetmeal();
            return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmeal);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal =  setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
