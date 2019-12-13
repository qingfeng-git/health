package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.PermissionMapper;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: UserServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-10 17:48
 * @Description: //TODO
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public User findByUsername(String s) {
        //根据账号查到用户
        User user =  userMapper.findByUsername(s);
        if (user == null){
            return null;
        }
        //根据用户id查询角色
        Integer id = user.getId();
        Set<Role> roleSet =  roleMapper.findByRole(id);
        if (roleSet != null && roleSet.size()>0){
            for (Role role : roleSet) {
                //根据角色ID查询其中的权限集合
                Integer id1 = role.getId();
                Set<Permission> set =  permissionMapper.ByIdfindPermission(id1);
                if (set != null && set.size()>0){
                    role.setPermissions(set);
                }
            }
            user.setRoles(roleSet);
        }
        return user;
    }
    @Override
    public List<Map<String, Object>> findById(String username) {

        User user = userMapper.findByUsername(username);
        Integer id = user.getId();
        //根据用户id查询角色,
        Role role = userMapper.findRole(id);
        Map<String,Object> map1 =  new HashMap<String,Object>();
        map1.put("rid",role.getId());
        //根据角色id查询菜单一级目录
        List<Map<String, Object>> list = userMapper.findMenu(role.getId());
        for (Map<String, Object> map : list) {

            Integer id1 = (Integer) map.get("id");
            map1.put("id",id1);
            List<Map<String, Object>> list2 = userMapper.findPath(map1);
            map.put("children", list2);
        }
        return list;
    }
}
