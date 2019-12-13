package com.itheima.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.itheima.controller
 * @ClassName: AuthentyController
 * @Author: QingFeng
 * @Date: 2019-12-10 21:12
 * @Description: //TODO
 */
@RestController
@RequestMapping("/authenty")
public class AuthentyController {

    @RequestMapping("/checkitem")
    public Map<String,Boolean> checkitem(){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("add",false);
        map.put("edit",false);
        map.put("delete",false);


        //获取到权限框架中的上下文作用域中用户的登录信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取对象中的权限集合
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        //遍历集合,得到每一个权限关键字
        for (GrantedAuthority authority : authorities) {
            String s = authority.getAuthority();
            //将字符串放在第一个比较,可以避免空指针异常
            //遍历的时候只可以得到一个权限,如果有一个权限相同,则使用countinu跳出本次循环
            if ("CHECKITEM_ADD".equals(s)){
                map.put("add",true);
                continue;
            }
            if ("CHECKITEM_EDIT".equals(s)){
                map.put("edit",true);
                continue;
            }
            if ("CHECKITEM_DELETE".equals(s)){
                map.put("delete",true);
                continue;
            }
        }
        return map;
    }
}
