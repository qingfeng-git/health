package com.itheima.mapper;

import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User findByUsername(String s);
    Role findRole(Integer id);
    List<Map<String, Object>> findMenu(Integer id);

    List<Map<String, Object>> findPath(Map<String, Object> title);
}
