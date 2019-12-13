package com.itheima.service;

import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUsername(String s);
    List<Map<String, Object>> findById(String username);
}
