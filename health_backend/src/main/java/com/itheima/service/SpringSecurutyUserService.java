package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @PackageName: com.itheima.service
 * @ClassName: SpringSecurutyUserService
 * @Author: QingFeng
 * @Date: 2019-12-10 13:25
 * @Description: //TODO
 */
@Component
public class SpringSecurutyUserService implements UserDetailsService {
    //调用adubbo服务
    @Reference
    private UserService userService;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据前台传过来的账号来查找数据库
        User user =  userService.findByUsername(s);
        //判断是否查询到用户
        if (user == null){
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        //设置授权
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(s,user.getPassword(),list);
        return user1;
    }
}
