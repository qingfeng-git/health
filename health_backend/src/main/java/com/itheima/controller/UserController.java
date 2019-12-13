package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: UserController
 * @Author: QingFeng
 * @Date: 2019-12-10 20:15
 * @Description: //TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/findusername")
    public Result findusername(){
        //从权限认证框架中获取数据,框架自动将信息存入到上下文作用于当中
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
    @RequestMapping("/getRole")
    public Result getRole(String username){
        try {
            List<Map<String,Object>> list = userService.findById(username);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
    }
}
